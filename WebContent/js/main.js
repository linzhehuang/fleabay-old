/**
 * 
 */

//$('.dropdown-menu').on('click',function(e) {
//	var $target = $(e.target);
//	console.log($target);
//	$target.is('li') && $('#dropdownText').text($target.text())	
//});
//
function $$() {
  var selfData = {
    attrs : arguments[1].attrs || {},
    events : arguments[1].events || {},
    html : arguments[1].html || ""
  }
  var selfNode = document.createElement(arguments[0]);
  for(var key in selfData.attrs) {
    selfNode.setAttribute(key, selfData.attrs[key]);
  }
  for(var event in selfData.events) {
    selfNode.addEventListener(event, selfData.events[event], false);
  }
  selfNode.innerHTML = selfData.html;
  return selfNode;
}
// 创建商品项目卡片
function createGoodCard(id, name, price, spec) {
	var imgPath = "./good-image/" + id + ".jpg";
	var detailURL = "./detail.html#" + id;
	var goodCard = $$('div',{
		attrs : {
			'class' : 'col good-card'
		}
	});
	var img = $$('div', {
		attrs : {
			'class' : 'img',
			'style' : 'background-image: url("' + imgPath + '");'
		}
	});
	var info = $$('div', {
		attrs : {
			'class' : 'info'
		}
	});
	info.appendChild($$('span', {
		attrs : {
			'class' : 'name'
		},
		html : '<a href="' + detailURL + '" >' + name + "</a>"
	}));
	info.appendChild($$('span', {
		attrs : {
			'class' : 'price'
		},
		html : price + "元/" + spec
	}));
	goodCard.appendChild(img);
	goodCard.appendChild(info);
	return goodCard;
}
// 
function createEmptyCard() {
	var emptyCard = $$('div',{
		attrs : {
			'class' : 'col'
		}
	});
	return emptyCard;
}
// 渲染商品列表
function renderGoodList(goodList) {
	$('#goodList').empty();
	var i = 0;
	for(i = 0;i < goodList.length-3;i += 3) {
		var col = $$('div', {
			attrs : {
				'class' : 'row'
			}
		});
		for(var j = 0;j < 3;j++) {
			var good = goodList[i+j];
			col.appendChild(createGoodCard(
					good.id, good.name, good.price, good.spec
			));
		}
		$('#goodList').append(col);
	}
	var col = $$('div', {
		attrs : {
			'class' : 'row'
		}
	});
	var max = i+3;
	for(;i < max;i++) {
		if(i >= goodList.length) {
			col.appendChild(createEmptyCard());
		} else {
			var good = goodList[i];
			col.appendChild(createGoodCard(good.id, good.name, good.price, good.spec));
		}
	}
	$('#goodList').append(col);
}
// 渲染类型下拉框
function renderTypeDropdown(typeList) {
	$('#dropdownMenuType').empty();
	for(var i = 0;i < typeList.length;i++) {
		$('#dropdownMenuType').append($$('li',{
			html : typeList[i]
		}));
	}
}

// 绑定下拉框事件
$('#dropdownMenuType').on('click',function(e) {
	var $target = $(e.target);
	var type = $target.text();
	$.ajax({
		type : "post",
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		url : "./getGoodListByType.do",
		data: JSON.stringify({
			"type" : type
		}),
		success : function(goodList) {
			console.log(goodList);
		    renderGoodList(goodList);
		}
	});
});
// 
$('#allGood').on('click',function() {
	getAllGood();
});
// 绑定搜索按钮
$('#searchButton').on('click', function() {
	$.ajax({
		type : "post",
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		url : "./getGoodListByKeyword.do",
		data: JSON.stringify({
			"keyword" : $('#inputKeyword').val()
		}),
		success : function(goodList) {
			console.log(goodList);
			renderGoodList(goodList);
			$('#goodList').append('<div>共 ' + goodList.length + ' 个搜索记录包含关键字“' + $('#inputKeyword').val() +'”</div>');
		}
	});
});

// 获取商品列表
function getAllGood() {
	$.ajax({
		type : "post",
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		url : "./getGoodList.do",
		data: "",
		success : function(goodList) {
			console.log(goodList);
		    renderGoodList(goodList);
		}
	});
}
// 获取分类列表
$.ajax({
	type : "post",
	dataType : "json",
	contentType : "application/json;charset=UTF-8",
	url : "./getTypeList.do",
	data: "",
	success : function(typeList) {
		console.log(typeList);
	    renderTypeDropdown(typeList);
	}
});
// 
getAllGood();