var PrmCache = function(scope){
	this.scope = scope;
}
PrmCache.prototype = {
	put:function(key, value){
		if(window.sessionStorage){
			alert(value.length);
			sessionStorage.setItem(key,JSON.stringify(value)) ;
		}
	},
	get:function(key){
		if(window.sessionStorage){
			var value = sessionStorage.getItem(key) ;
			if (value) {
				JSON.parse(value);
			}else{
				return null;
			}
		}else{
			return {};
		}
	},
	clear:function(){
		if(window.sessionStorage){
			sessionStorage.clear(key) ;
		}
	},
	clearByKey:function(key){
		if(window.sessionStorage){
			sessionStorage.removeItem(key) ;
		}
	}
};

var getDefKey = function(){
	var href = window.location.href;
	if (href.lastIndexOf('#') > -1){
		href = href.substring(0,href.lastIndexOf('#'));
	}
	var urls = href.split('?');
	if (urls.length !=2){
		return href;
	}
	var params = urls[1].split("&"),href = urls[0];
	for (var i = 0; i < params.length; i++){
		if (params[i].split("=").length == 2){
			href = href + (href.lastIndexOf('?') == -1 ? '?' : '&') +  params[i];
		}
	}
	return href;
}
var formToJson = function (data) {
    data=data.replace(/&/g,"\",\"");
    data=data.replace(/=/g,"\":\"");
    data="{\""+data+"\"}";
    return data;
 }
/**
 * 查询条件回显工具类,所有的查询条件都保存在main.jsp 的缓存对象中
 * @param {Object} $
 * @memberOf {TypeName} 
 */
;
(function( $, undefined ) {
	
	
	//参数cache定义
	var paramEchoCache = new PrmCache("paramEcho");
	
	
	//数组数据序列化json数据
	var serializeJson = function(array){
		var json = {};
		$(array).each(function(){
			if(json[this.name]){
				if($.isArray(json[this.name])){
					json[this.name].push(this.value);
				}else{
					json[this.name]=[json[this.name],this.value];
				}
			}else{
				json[this.name]=this.value;
			}
		});
		return json;
	}
	
	
	//保存form查询条件
	$.fn.saveQueryParam = function(){
		var forms = [];
		$(this).each(function(i){//一个页面上可能有多个form
			forms[$(this).attr('id')] = $(this).serializeArray();
		});
		paramEchoCache.put(getDefKey(),forms);
	}
	
	//获取查询条件
	$.fn.getQueryParam = function(){
		var forms = paramEchoCache.get(getDefKey());
		if(forms){
			var fields = forms[$(this).attr('id')];
			if(fields){
				return serializeJson(fields);
			}
		}
		return {};
	}
	
	
	
	$.extend({
		//回显查询条件
		echoQueryParam:function(){
			var forms = paramEchoCache.get(getDefKey());
			if(forms){//多个form
				for(var key in forms){//放置了数据
					var fields = forms[key];
					$.each(fields, function(i, field){
						var f = $('#' + key).find('[name="' + field.name + '"]');
						if(f.length == 0){//兼容火狐浏览器
							f = $('#' + key).parent().find('[name="' + field.name + '"]');
						}
						var vv = field.value;
						if(f.is('input')){
							var _type = f.attr("type");
							if (_type == 'text' || _type == 'hidden'){
								f.val(vv);
							} else if (_type == 'radio'){
								if (f.val() == vv){
									f.attr("checked","checked")
								} 
							} else if (_type == 'checkbox'){
								var arr = vv.split(',');
								for (var i = 0; i < arr.length ;i++){
									if ($(this).val() == arr[i]){
										$(this).attr("checked","checked")
									} 
								}
							} 
						}else if(f.is('select')){
							f.val(vv);
							f.attr('_defaultValue',vv);
							f.trigger('change');//解决firefox下通过js改变值后不触发onchange事件,这里手动触发
						}else if(f.is('textarea')){
							f.val(vv);
						}
					});
				}
			}
		},
		//得到保存的查询条件,对于一个页面只有一个form的情况
		getQueryParams:function(){
			var forms = paramEchoCache.get(getDefKey());
			if(forms){
				for(var id in forms){
					var fields = forms[id];
					if(fields){
						return serializeJson(fields);
					}
				}
				
			}
			return {};
		},
		//清除参数cache
		clearParamCache:function(){
			paramEchoCache.clearByKey(getDefKey());
		}
	});
	
})(jQuery);

$().ready(function() {
	//页面加载完成后回显查询条件
	setTimeout('$.echoQueryParam()',200);
	setTimeout('$.clearParamCache()',300);
});