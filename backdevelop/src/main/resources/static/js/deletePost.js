function postDelete () {
	let primaryIDs = document.getElementsByName('checkbox0')
	let ids = ''
	for (let i = 0; i < primaryIDs.length; i++) {
		if (primaryIDs[i].type === 'checkbox' && primaryIDs[i].checked === true) {
			ids += primaryIDs[i].value + ';'
		}
	}
	if (ids === '') {
		window.alert('请选择要删除的帖子!')
	} else {
		if (window.confirm('确定删除吗？') === true) {
			let checkedIdObj = document.getElementById('checkedId')
			window.alert('删除成功')
			checkedIdObj.value = ids
		}
	}
}

function init () {
	let Obj = document.getElementsByName('checkbox0')
	let tableObj = document.getElementById('searchPost')
	let i = 1
	let k
	for (k in Obj) {
		Obj[k].value = tableObj.rows[i].cells[0].innerHTML
		i++
	}
}

window.onload = function () {
	init()
}
