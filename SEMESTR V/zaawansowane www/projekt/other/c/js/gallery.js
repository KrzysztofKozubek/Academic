fl=true;
$(document).ready(function(){
	$(".carousel_box").jCarouselLite({
		btnNext: ".next",
		btnPrev: ".prev"
	});	
	
	
})

$(window).load(function(){
	
	var width=$('.carousel_box ul').width();
	var left=parseInt($('.carousel_box ul').css('left'))
	
	$('.carousel_box ul').css({left:left})
	$('.carousel_box ul').css({width:width+54})
	
	var amount=$(".carousel_box li").length;

	var act=Math.round((amount/3)+1);
	
	$('.carousel_box li').eq(act).addClass('active').css({width:214, height:337})
	
	
	$('.next').click(function(){
		if (fl) {
			fl=false;
			if (act==Math.round((amount/3)*2)+1) {
				act=Math.round((amount/3))+2	
			} else { 
				act++ 
			}
			$('.carousel_box .active').removeClass('active').stop().animate({width:160, height:252}, function(){fl=true})
			$('.carousel_box li').eq(act).addClass('active').stop().animate({width:214, height:337})
		}
	})
	
	$('.prev').click(function(){
		if (fl) {
			fl=false;
			if (act==1) {
				act=Math.round((amount/3))	
			} else { 
				act--
			}
			$('.carousel_box .active').removeClass('active').stop().animate({width:160, height:252}, function(){fl=true})
			$('.carousel_box li').eq(act).addClass('active').stop().animate({width:214, height:337})
		}
	})						
})