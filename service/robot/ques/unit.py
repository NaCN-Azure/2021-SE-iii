import io
import json
import jieba.posseg
# # 将自定义字典写入文件
# result = []
# with(open("./data/userdict.txt","r",encoding="utf-8")) as fr:
#     vocablist=fr.readlines()
#     for one in vocablist:
#         if str(one).strip()!="":
#             temp=str(one).strip()+" "+str(15)+" nr"+"\n"
#             result.append(temp)
# with(open("./data/userdict2.txt","w",encoding="utf-8")) as fw:
#     for one in result:
#         fw.write(one)

import sys, os
from sklearn.naive_bayes import MultinomialNB
import re

import jieba
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB

# 获取所有的文件
def getfilelist(root_path):
    file_path_list=[]
    file_name=[]
    walk = os.walk(root_path)
    for root, dirs, files in walk:
        for name in files:
            filepath = os.path.join(root, name)
            file_name.append(name)
            file_path_list.append(filepath)
    # print(file_name)
    # print(file_path_list)
    # print(len(file_path_list))
    return file_path_list


class Question_classify():
    def __init__(self):
        # 读取训练数据
        self.train_x,self.train_y=self.read_train_data()
        # 训练模型
        self.model=self.train_model_NB()
    # 获取训练数据
    def read_train_data(self):
        train_x=[]
        train_y=[]
        file_list=getfilelist("service/robot/ques/data/question/") # 遍历所有文件
        # 遍历所有文件
        for one_file in file_list:
            # 获取文件名中的数字
            num = re.sub(r'\D', "", one_file) # 替换文件名字符串的非数字字符为空，即提取字符中的数字，'\D'表示非数字字符
            # 如果该文件名有数字，则读取该文件
            if str(num).strip()!="":
                # 设置当前文件下的数据标签，即该文件内数据对应的类别标签，对应训练样本的y数据
                label_num=int(num)
                # 读取文件内容
                with(open(one_file,"r",encoding="utf-8")) as fr:
                    data_list=fr.readlines()
                    for one_line in data_list: # 一行为一条训练样本
                        word_list=list(jieba.cut(str(one_line).strip()))
                        # 将这一行加入结果集，每条训练样本的x数据由通过空格相连的词组构成
                        train_x.append(" ".join(word_list))
                        train_y.append(label_num)
        return train_x,train_y

    # 训练并测试贝叶斯分类器模型-Naive Bayes
    def train_model_NB(self):
        X_train, y_train = self.train_x, self.train_y
        self.tv = TfidfVectorizer()
        train_data = self.tv.fit_transform(X_train).toarray() # tfidf向量化模型是基于全部训练数据训练得到，后续预测的时候也需要使用
        clf = MultinomialNB(alpha=0.01) # 建立MultinomialNB模型
        clf.fit(train_data, y_train) # 训练MultinomialNB模型
        return clf

    # 预测
    def predict(self,question):
        question=[" ".join(list(jieba.cut(question)))]
        test_data=self.tv.transform(question).toarray() # 用训练号的词向量化模型，向量化输入问句
        y_predict = self.model.predict(test_data)[0] # 返回概率最大的预测类别
        # print("question type:",y_predict)
        return y_predict

# Disable
def blockPrint():
    sys.stdout = open(os.devnull, 'w')

# Restore
def enablePrint():
    sys.stdout = sys.__stdout__
# blockPrint()

# enablePrint()

class Question():
    def __init__(self):
        # 初始化相关设置：读取词汇表，训练分类器，连接数据库
        self.init_config()

    def init_config(self):
        # # 读取词汇表
        # with(open("./data/vocabulary.txt","r",encoding="utf-8")) as fr:
        #     vocab_list=fr.readlines()
        # vocab_dict={}
        # vocablist=[]
        # for one in vocab_list:
        #     word_id,word=str(one).strip().split(":")
        #     vocab_dict[str(word).strip()]=int(word_id)
        #     vocablist.append(str(word).strip())
        # # print(vocab_dict)
        # self.vocab=vocab_dict

        # 训练分类器
        self.classify_model=Question_classify()
        # 读取问题模板
        with(open("service/robot/ques/data/question/question_classification.txt","r",encoding="utf-8-sig")) as f:
            question_mode_list=f.readlines()
        self.question_mode_dict={}
        for one_mode in question_mode_list:
            # 读取一行
            mode_id,mode_str=str(one_mode).strip().split(":")
            # 处理一行，并存入
            self.question_mode_dict[int(mode_id)]=str(mode_str).strip()
        # print(self.question_mode_dict)

        # 创建问题模板对象
        self.questiontemplate=QuestionTemplate()

    def question_process(self,question):
        # 接收问题
        self.raw_question=str(question).strip()
        # 对问题进行词性标注
        self.pos_quesiton=self.question_posseg()
        # 得到问题的模板
        self.question_template_id_str=self.get_question_template()
        # 查询图数据库,得到答案
        self.answer=self.query_template()
        return(self.answer)

    # 分词和词性标注
    def question_posseg(self):
        # 添加自定义词典
        jieba.load_userdict("service/robot/ques/data/extract.txt")
        # 去除无用标点符号
        clean_question = re.sub("[\s+\.\!\/_,$%^*(+\"\')]+|[+——()?【】“”！，。？、~@#￥%……&*（）]+","",self.raw_question)
        self.clean_question=clean_question
        # 分词
        question_seged=jieba.posseg.cut(str(clean_question))
        result=[]
        question_word, question_flag = [], []
        for w in question_seged:
            temp_word=f"{w.word}/{w.flag}"
            result.append(temp_word) # result格式为“词/词性”
            # 预处理问题，为后续的预测和查询存储数据
            word, flag = w.word,w.flag
            question_word.append(str(word).strip()) # 删除头尾空格、/n、/t
            question_flag.append(str(flag).strip())
        assert len(question_flag) == len(question_word)
        self.question_word = question_word
        self.question_flag = question_flag
        # print(result)
        return result

    def get_question_template(self):
        # 抽象问题
        for item in ['nr','ns','n','nrt','nl']:
            while (item in self.question_flag):
                ix=self.question_flag.index(item) # 查找相应词性的位置
                self.question_word[ix]=item # 词替换为词性
                self.question_flag[ix]=item+"ed" # 修改词性，表示已替换了
        # 将问题转化字符串
        str_question="".join(self.question_word)
        # print("抽象问题为：",str_question)
        # 通过分类器获取问题模板编号
        question_template_num=self.classify_model.predict(str_question)
        # print("使用模板编号：",question_template_num)
        question_template=self.question_mode_dict[question_template_num]
        # print("问题模板：",question_template)
        question_template_id_str=str(question_template_num)+"\t"+question_template
        return question_template_id_str


    # 根据问题模板的具体类容，构造cql语句，并查询
    def query_template(self):
        # 调用问题模板类中的获取答案的方法
        try:
            answer=self.questiontemplate.get_question_answer(self.pos_quesiton,self.question_template_id_str)
        except:
            answer="我也还不知道！"
        return answer


class QuestionTemplate():
    def __init__(self):
        self.q_template_dict={
            0:self.get_entity_near,
            1:self.get_entity_description,
            2:self.get_relationship,
            3:self.get_another,
            4:self.get_type_node
        }
    def get_question_answer(self,question,template):
        # 如果问题模板的格式不正确则结束
        assert len(str(template).strip().split("\t"))==2
        template_id,template_str=int(str(template).strip().split("\t")[0]),str(template).strip().split("\t")[1]
        self.template_id=template_id
        self.template_str2list=str(template_str).split()
        # 预处理问题
        question_word,question_flag=[],[]
        for one in question:
            word, flag = one.split("/")
            question_word.append(str(word).strip())
            question_flag.append(str(flag).strip())
        assert len(question_flag)==len(question_word)
        self.question_word=question_word
        self.question_flag=question_flag
        self.raw_question=question
        # 根据问题模板来做对应的处理，获取答案
        answer=self.q_template_dict[template_id]()
        dict = {}
        dict["AnswerType"]=template_id
        dict["AnswerMember"]=answer
        return dict

    def get_entity_name(self):
        tag_index = 0
        for i in range(len(self.question_flag)):
            if(self.question_flag[i].startswith('n')):
                tag_index = i
                break
        entity_name = self.question_word[tag_index]
        return entity_name

    def get_entity_name_last(self):
        tag_index = 0
        for i in range(len(self.question_flag)):
            if(self.question_flag[i].startswith('n')):
                tag_index = i
                if(self.question_word[i]==self.get_entity_name()):
                    continue
                else:
                    break
        entity_name = self.question_word[tag_index]
        return entity_name

    # 0:节点
    def get_entity_near(self):
        answer =  self.get_entity_name()
        list = []
        list.append(answer)
        return list
    # 1:描述节点
    def get_entity_description(self):
        return self.get_entity_near()

    def get_relationship(self):
        start = self.get_entity_name()
        end = self.get_entity_name_last()
        list=[]
        list.append(start)
        list.append(end)
        return list

    def get_another(self):
        return self.get_relationship()

    def get_type_node(self):
        return self.get_entity_near()

def ques(ask_question):
    sys.stderr = io.TextIOWrapper(sys.stderr.buffer,encoding='utf-8')
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')
    que=Question()
    # def enablePrint():
    #     sys.stdout = sys.__stdout__
    # enablePrint()
    result=que.question_process(ask_question)
    print(result["AnswerType"])
    list = result["AnswerMember"]
    for item in list:
        print(item)

if __name__ =='__main__':
    ques(sys.argv[1])


