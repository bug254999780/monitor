// JavaScript Document

function Index()
{
	this.init();
}

Index.prototype={
	init:function()
	{
		var _this = this;
		_this.menu();
		_this.sideLt();
		_this.showPic();
		_this.report();
		_this.warningRecordBtn();
	},
	report:function()
	{
		var cIndex = 0;
		$(".result h3 a").bind("click",function()
		{
			cIndex = $(this).index();
			$(this).addClass("curr").siblings("a").removeClass("curr");	
			$(".resultCont .cBox").eq(cIndex).show().siblings().hide();
		});
	},
	menu:function()
	{
		$(".menu a").bind("click",function()
		{
			$(this).addClass("curr").siblings("a").removeClass("curr");
		});
	},
	sideLt:function()
	{
		var aSpan = $(".sideLt ul li span");
		var aDd   = $(".sideLt dl dd");
		var aDt   = $(".sideLt dl dt");
		aSpan.live("click",function()
		{
			if(!$(this).hasClass("all"))
			{
				$(this).addClass("all");
				$(this).closest("li").find("dl").slideDown();
			}
			else
			{
				$(this).removeClass("all");
				$(this).closest("li").find("dl").slideUp();
			}
		});
		
		aDt.live("click",function()
		{
			if(!$(this).hasClass("dt"))
			{
				$(this).addClass("dt");
				$(this).closest("dl").find("dd").slideDown();
			}
			else
			{
				$(this).removeClass("dt");
				$(this).closest("dl").find("dd").slideUp();
			}
		});
		
		aDd.live("click",function()
		{
			aDd.removeClass("scurr");
			$('[id^=dd]').removeClass("scurr");
			$(this).addClass("scurr");
		});	
	},
	showPic:function()
	{
		var _this  = this;
		$(".boxRt").each(function()
		{
			var self = $(this);
			self.find("li").bind("click",function()
			{
				$(".popPic").html($(this).find("a").html());
				_this.showPop($(".pop"),$(".mark"));
			});
		});
		
		$(".close").bind("click",function()
		{
			_this.hidePop($(".pop"),$(".mark"));
		});
	},
	showPop:function(iTck,iMark)
	{
		var sTop=$(window).scrollTop(),
		cWidth=$(window).width(),
		cHeight=$(window).height(),
		popWidth=iTck.width();
		popHeight=iTck.height();
	
		iMark.show();
		iTck.show();
		iMark.height($(document).height());
		iTck.css({"left":(cWidth-popWidth)/2,"top":(cHeight-popHeight)/2+sTop});
	},
	hidePop:function(iTck,iMark)
	{
		iTck.hide();
		iMark.hide();
	},
	warningRecordBtn:function(){
		var _this = this;
		//点击【确定】，修改数据
		$("#warningRecordBtn").click(function(){
			var dataNo = $(this).attr("data-id");
			console.log(dataNo);
			_this.requestRemoteData("monitor/MonitorController/updateAlarmRecord", {"dataNo":dataNo}, "post", function(data){
				if(data.result == "success"){
					 location.reload();
				}
			});
		});
	},
	requestRemoteData:function(url, data, type, callback){
		if(!data){
			$.param(data);
		}
		$.ajax({
            type:type,
            url: url,
            data: data,
            dataType: "json",
            success:callback
       });
	}
};

$(function()
{
	var index = new Index();
});
