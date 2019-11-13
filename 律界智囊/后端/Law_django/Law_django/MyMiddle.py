from django.utils.deprecation import MiddlewareMixin


class MyCorsMiddle(MiddlewareMixin):
    def process_response(self, request, response):
        response['Access-Control-Allow-Origin'] = '*'
        if request.method == 'OPTIONS':
            response['Access-Control-Allow-Headers'] = '*'
        return response
