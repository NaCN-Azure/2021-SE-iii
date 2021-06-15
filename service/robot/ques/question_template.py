import json

from query import Query
import re
from py2neo import Graph,Node,Relationship,NodeMatcher



class QuestionTemplate():
    def __init__(self):
        self.q_template_dict={
            0:self.get_entity_near
        }
        self.graph = Query()


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
        return json.dumps(dict)

    def get_entity_name(self):
        tag_index = 0
        for i in range(len(self.question_flag)):
            if(self.question_flag[i].startswith('n')):
                tag_index = i
                break
        entity_name = self.question_word[tag_index]
        return entity_name

    # 0:地址
    def get_entity_near(self):
        entity_name = self.get_entity_name()
        return entity_name


