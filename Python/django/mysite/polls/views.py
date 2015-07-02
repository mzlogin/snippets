from django.shortcuts import render, get_object_or_404
from django.http import HttpResponse, Http404
from django.template import Context, loader
from polls.models import Poll

def index(request):
    lastest_poll_list = Poll.objects.order_by('-pub_date')[:5]
    template = loader.get_template('polls/index.html')
    context = Context({
        'lastest_poll_list': lastest_poll_list,
    })
    return HttpResponse(template.render(context))

def detail(request, poll_id):
    poll = get_object_or_404(Poll, pk=poll_id)
    return render(request, 'polls/detail.html', {'poll': poll})

def results(request, poll_id):
    return HttpResponse("You're looking at the results of poll %s." % poll_id)

def vote(request, poll_id):
    return HttpResponse("You're voting on poll %s." % poll_id)
