from django.conf.urls import url
from law import views
# 在应用的urls文件中进行url配置的时候：
# 严格匹配开头和结尾
urlpatterns = [
    url(r'^fact_get$', views.fact_get),
    url(r'^predict$', views.predict),
    url(r'^recommend$', views.recommend),
    url(r'^recommend_get$', views.recommend_get),
    url(r'^new_classify2$', views.new_classify2),
    url(r'^classify$', views.classify),
    url(r'^classify2$', views.classify2),
    url(r'^final$', views.final),
    url(r'^tiaoli2_classify_sum$', views.tiaoli2_classify_sum),
    url(r'^tiaoli2_classify$', views.tiaoli2_classify),
    url(r'^tiaoli2_classify2$', views.tiaoli2_classify2),
    url(r'^tiaoli2$', views.tiaoli2),
    url(r'^baike_classify$', views.baike_classify),
    url(r'^baike$', views.baike),
    url(r'^login$', views.login),
    url(r'^register$', views.register),
    url(r'^delete_history$', views.delete_history),
    url(r'^add_history$', views.add_history),
    url(r'^xingfa', views.xingfa),
    url(r'^anli_find', views.anli_find),
    url(r'^anli_list', views.anli_list)
]