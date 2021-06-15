import io
import sys
from preprocess_data import Question

def ques():
    que=Question()
    def enablePrint():
        sys.stdout = sys.__stdout__
    enablePrint()
    result=que.question_process("节点A附近")
    print(result)
    return result

if __name__ =='__main__':
    ques()