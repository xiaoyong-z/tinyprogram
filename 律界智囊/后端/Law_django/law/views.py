from django.shortcuts import render
from django.http import HttpResponse
from django.template import loader, RequestContext
from Predict.predict import predict_entrance
from django.http import JsonResponse
from recommendation.recommend import recommend_article
from mongo.mongo_interface import *
import json
from bson import ObjectId

class JSONEncoder(json.JSONEncoder):
    def default(self, o):
        if isinstance(o, ObjectId):
            return str(o)
        return json.JSONEncoder.default(self, o)


def fact_get(request):
    return render(request, "law/fact_get.html")

def predict(request):
    if(len(list(request.POST.items())) == 0):
        return HttpResponse("数据输入有误")
    else:
        fact = list(request.POST.items())[0][0]
        result = predict_entrance(fact)
        return JsonResponse(result, json_dumps_params={'ensure_ascii':False})

def recommend(request):
    if (len(list(request.POST.items())) == 0):
        return HttpResponse("数据输入有误")
    else:
        num = int(list(request.POST.items())[0][0])
        result = recommend_article(num)
        # for index, i in enumerate(result):
        #     result[index] = JSONEncoder(ensure_ascii=False).encode(i)
        return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def new_classify2(request):
    result = mongo_new_classify2()
    return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def classify(request):
    if (len(list(request.POST.items())) == 0):
        return HttpResponse("数据输入有误")
    else:
        crime = list(request.POST.items())[0][0]
        result = mongo_classify(crime)
        return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def classify2(request):
    result = mongo_classify2()
    return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def final(request):
    if (len(list(request.POST.items())) == 0):
        return HttpResponse("数据输入有误")
    else:
        crime = list(request.POST.items())[0][0]
        result = mongo_final(crime)
        return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def tiaoli2_classify_sum(request):
    result = mongo_tiaoli2_classify_sum()
    return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def tiaoli2_classify(request):
    if (len(list(request.POST.items())) == 0):
        return HttpResponse("数据输入有误")
    else:
        crime = list(request.POST.items())[0][0]
        result = mongo_tiaoli2_classify(crime)
        return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def tiaoli2_classify2(request):
    result = mongo_tiaoli2_classify2()
    return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def baike_classify(request):
    result = mongo_baike_classify()
    return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def baike(request):
    if (len(list(request.POST.items())) == 0):
        return HttpResponse("数据输入有误")
    else:
        crime = list(request.POST.items())[0][0]
        result = mongo_baike(crime)
        return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def login(request):
    if (len(list(request.POST.items())) == 0):
        return HttpResponse("数据输入有误")
    else:
        user = list(request.POST.items())[0][1]
        passwd = list(request.POST.items())[1][1]
        result = mongo_login(user, passwd)
        return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def xingfa(request):
    if (len(list(request.POST.items())) == 0):
        return HttpResponse("数据输入有误")
    else:
        xingfa = list(request.POST.items())[0][0]
        result = mongo_xingfa(xingfa)
        return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def anli_find(request):
    if (len(list(request.POST.items())) == 0):
        return HttpResponse("数据输入有误")
    else:
        anli = [j for (i,j)  in list(request.POST.items())]
        result = mongo_anlifind(anli)
        return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def anli_list(request):
    result = mongo_anlilist()
    return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)


def register(request):
    if (len(list(request.POST.items())) == 0):
        return HttpResponse("数据输入有误")
    else:
        user = list(request.POST.items())[0][1]
        passwd = list(request.POST.items())[1][1]
        result = mongo_register(user, passwd)
        return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def delete_history(request):
    if (len(list(request.POST.items())) == 0):
        return HttpResponse("数据输入有误")
    else:
        user = list(request.POST.items())[0][0]
        result = mongo_delete_history(user)
        return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def add_history(request):
    if (len(list(request.POST.items())) == 0):
        return HttpResponse("数据输入有误")
    else:
        user = list(request.POST.items())[0][1]
        history = int(list(request.POST.items())[1][1])
        result = mongo_add_history(user, history)
        return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def tiaoli2(request):
    if (len(list(request.POST.items())) == 0):
        return HttpResponse("数据输入有误")
    else:
        crime = list(request.POST.items())[0][0]
        result = mongo_tiaoli2(crime)
        return JsonResponse(result, json_dumps_params={'ensure_ascii': False}, safe=False)

def recommend_get(request):
    return render(request, "law/recommend.html")



