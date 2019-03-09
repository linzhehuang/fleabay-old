/**
 * 
 */
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
function createDeleteButton() {
	
}
function renderGoodList(goodList) {
	$('#tbody').empty();
	var columnList = ["name", "type", "price", "stock", "spec", "remark"];
	for(var i = 0;i < goodList.length;i++) {
		var good = goodList[i];
		var line = $$('tr',{});
		// 添加数据
		for(var j = 0;j < columnList.length;j++) {
			var cellValue = good[columnList[j]];
			line.appendChild($$('td', {
				attrs : {
					'class' : 'center',
					'title' : cellValue
				},
				html : cellValue
			}));
		}
		// 添加操作按钮
		var buttonGroup = $$('td', {
			attrs : {
				'class' : 'center'
			}
		});
		buttonGroup.appendChild($$('span',{
			attrs : {
				'class' : 'mBtn',
				'id' : good.id
			},
			html : '删除',
			events : {
				'click' : function() {
					if(confirm("是否删除？")) deleteGoodById(this.id);
				}
			}
		}));
		buttonGroup.appendChild($$('span',{
			attrs : {
				'class' : 'mBtn',
				'id' : good.id
			},
			html : '修改',
			events : {
				'click' : function() {
					window.location.href = "./updategood.html#" + this.id;
				}
			}
		}));
		line.appendChild(buttonGroup);
		// 
		$('#tbody').append(line);
	}
}
function deleteGoodById(id) {
	$.ajax({
		type : "post",
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		url : "./deleteGoodById.do",
		data: JSON.stringify({
			'id' : id
		}),
		success : function(responseData) {
			if(responseData.success) {
				alert("删除成功！");
				loadGoodList();
			} else {
				alert("删除失败");
			}
		}
	});
}
function loadGoodList() {
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
loadGoodList();