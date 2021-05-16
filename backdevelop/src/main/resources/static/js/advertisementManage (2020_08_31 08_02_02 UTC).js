/**
 * 分页函数
 * pno--页数
 * psize--每页显示记录数
 * 分页部分是从真实数据行开始，因而存在加减某个常数，以确定真正的记录数
 * 纯js分页实质是数据行全部加载，通过是否显示属性完成分页功能
 **/
function goPage (pno, psize) {
	let itable = document.getElementById('id_data')
	let num = itable.rows.length
	console.log(num)
	let totalPage = 0
	let pageSize = psize
	if (num / pageSize > parseInt(num / pageSize)) {
		totalPage = parseInt(num / pageSize) + 1
	} else {
		totalPage = parseInt(num / pageSize)
	}
	let currentPage = pno
	let startRow = (currentPage - 1) * pageSize + 1
	let endRow = currentPage * pageSize
	endRow = endRow > num ? num : endRow
	console.log(endRow)
	for (let i = 1; i < num + 1; i++) {
		let irow = itable.rows[i - 1]
		if (i >= startRow && i <= endRow) {
			irow.style.display = ''
		} else {
			irow.style.display = 'none'
		}
	}
	let tempStr = ''
	if (currentPage > 1) {
		tempStr += '<a href="#" onClick="goPage(' + 1 + ',' + psize + ')">首页&nbsp;&nbsp;&nbsp;</a>'
		tempStr += '<a href="#" onClick="goPage(' + (currentPage - 1) + ',' + psize + ')">上一页&nbsp;&nbsp;&nbsp;</a>'
	} else {
		tempStr += '首页&nbsp;&nbsp;&nbsp;'
		tempStr += '上一页&nbsp;&nbsp;&nbsp;'
	}
	tempStr += '(' + currentPage + '/' + totalPage + ')'
	if (currentPage < totalPage) {
		tempStr += '<a href="#" onClick="goPage(' + (currentPage + 1) + ',' + psize + ')">&nbsp;&nbsp;&nbsp;下一页</a>'
		tempStr += '<a href="#" onClick="goPage(' + totalPage + ',' + psize + ')">&nbsp;&nbsp;&nbsp;尾页</a>'
	} else {
		tempStr += '&nbsp;&nbsp;&nbsp;下一页'
		tempStr += '&nbsp;&nbsp;&nbsp;尾页'
	}
	document.getElementById('barcon').innerHTML = tempStr
}

function View (aid) {
	let mIdObj = document.getElementById('view_id')
	mIdObj.value = aid
}

function Modification (aid) {
	let mIdObj = document.getElementById('modification_id')
	mIdObj.value = aid
}

function Delete (aid) {
	if (window.confirm('你确定要删除广告(' + aid + ')吗？')) {
		let mIdObj = document.getElementById('delete_id')
		mIdObj.value = aid
	}
}

function init () {
	let deleteResultObj = document.getElementById('delete_result')
	goPage(1, 10)
	let itable = document.getElementById('id_data')
	let num = itable.rows.length
	for (let i = 0; i < num; i++) {
		let row = itable.rows[i]
		let aid = row.cells[0].innerText
		let td1 = document.createElement('td')
		td1.class="operation1"
		let button1 = document.createElement('button')
		button1.type = 'submit'
		button1.innerText = '查看'
		button1.value = aid
		button1.onclick = function () {
			let newEventText = this.value
			View(newEventText)
		}
		let button2 = document.createElement('button')
		button2.type = 'submit'
		button2.innerText = '修改'
		button2.value = aid
		button2.onclick = function () {
			let newEventText = this.value
			Modification(newEventText)
		}
		let button3 = document.createElement('button')
		button3.type = 'submit'
		button3.innerText = '删除'
		button3.value = aid
		button3.onclick = function () {
			let newEventText = this.value
			Delete(newEventText)
		}
		td1.append(button1)
		td1.append(button2)
		td1.append(button3)
		row.append(td1)
	}
	var mIdObj = document.getElementById('view_id')
	mIdObj.value = ''
	var mIdObj = document.getElementById('modification_id')
	mIdObj.value = ''
	var mIdObj = document.getElementById('delete_id')
	mIdObj.value = ''
}

window.onload = function () {
	init()
}
