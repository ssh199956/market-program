var billObj;
function deleteBill(obj) {
	$.ajax({
		type : "GET",
		url : path + "/bill/delBill",
		data : {
			id : obj.attr("billid")
		},
		dataType : "json",
		success : function(data) {
			if (data.delResult == "true") {// 删除成功：移除删除行
				cancleBtn();
				obj.parents("tr").remove();
			} else if (data.delResult == "false") {// 删除失败
				changeDLGContent("对不起，删除订单【" + obj.attr("billcc") + "】失败");
			} else if (data.delResult == "notexist") {
				changeDLGContent("对不起，订单【" + obj.attr("billcc") + "】不存在");
			}
		},
		error : function(data) {
			alert("对不起，删除失败");
		}
	});
}

function openYesOrNoDLG() {
	$('.zhezhao').css('display', 'block');
	$('#removeBi').fadeIn();
}

function cancleBtn() {
	$('.zhezhao').css('display', 'none');
	$('#removeBi').fadeOut();
}
function changeDLGContent(contentStr) {
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}

$(function() {
	$(".viewBill").on(
			"click",
			function() {
				window.location.href = path + "/bill/view?billid="
						+ $(this).attr("billid");
			});

	$(".modifyBill").on(
			"click",
			function() {
				var obj = $(this);
				window.location.href = path + "/bill/modify?billid="
						+ obj.attr("billid");
			});
	$('#no').click(function() {
		cancleBtn();
	});

	$('#yes').click(function() {
		deleteBill(billObj);
	});

	$(".deleteBill").on("click", function() {
		billObj = $(this);
		changeDLGContent("你确定要删除订单【" + billObj.attr("billcc") + "】吗？");
		openYesOrNoDLG();
	});
});