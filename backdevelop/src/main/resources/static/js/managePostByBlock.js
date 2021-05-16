function checkobject() {
	let blockObj=document.getElementsByName("block")
	let checkedBlock = new Array(blockObj.length);
	let isSelected=false;
	for (k in blockObj) {
		if (blockObj[k].checked) {
			checkedBlock.push(blockObj[k].value)
			isSelected=true;
		}
	}
	if(!isSelected) {
		for (k in blockObj) {
			checkedBlock.push(blockObj[k].value)
		}
	}
	let tableObj=document.getElementById("serchPost");
	let num = tableObj.rows.length
	for(let i=1;i<num;i++) {
		let row = tableObj.rows[i]
		let block = row.cells[3].innerText
		let istru=false;
		for(let j=0;j<checkedBlock.length;j++) {
			if(block === checkedBlock[j]) {
				istru=true;
			}

		}
		if (istru) {
			row.style.display=""
		} else {
			row.style.display="none"
		}
	}
}

function deleteCategories () {
	let primaryIDs = document.getElementsByName('checkbox0')
	let ids = ''
	for (let i = 0; i < primaryIDs.length; i++) {
		if (primaryIDs[i].type === 'checkbox' && primaryIDs[i].checked === true) {
			ids += primaryIDs[i].value + ';'
		}
	}
	if (ids === '') {
		window.alert('请选择要加精的帖子!')
	} else {
		if (window.confirm('确定加精吗？') === true) {
			let checkedIdObj = document.getElementById('checkedId')
			window.alert('加精成功')
			checkedIdObj.value = ids
		}
	}
}

function batchForbid () {
	let primaryIDs = document.getElementsByName('checkbox0')
	let ids = ''
	for (let i = 0; i < primaryIDs.length; i++) {
		if (primaryIDs[i].type === 'checkbox' && primaryIDs[i].checked === true) {
			ids += primaryIDs[i].value + ';'
		}
	}
	if (ids === '') {
		window.alert('请选择要禁止评论的帖子!')
	} else {
		if (window.confirm('确定禁止评论吗？') === true) {
			let checkedIdObj = document.getElementById('checkedId')
			window.alert('禁止评论成功！')
			checkedIdObj.value = ids
		}
	}
}

function deletePost() {
	let primaryIDs = document.getElementsByName('checkbox0')
	let ids = ''
	for (let i = 0; i < primaryIDs.length; i++) {
		if (primaryIDs[i].type === 'checkbox' && primaryIDs[i].checked === true) {
			ids += primaryIDs[i].value + ';'
		}
	}
	if (ids === '') {
		window.alert('请选择要批量删除的帖子!')
	} else {
		if (window.confirm('确定批量删除帖子吗？') === true) {
			let checkedIdObj = document.getElementById('checkedId')
			window.alert('批量删除成功！')
			checkedIdObj.value = ids
		}
	}
}

function foldPost() {
	let primaryIDs = document.getElementsByName('checkbox0')
	let ids = ''
	for (let i = 0; i < primaryIDs.length; i++) {
		if (primaryIDs[i].type === 'checkbox' && primaryIDs[i].checked === true) {
			ids += primaryIDs[i].value + ';'
		}
	}
	if (ids === '') {
		window.alert('请选择要批量折叠的帖子!')
	} else {
		if (window.confirm('确定批量折叠帖子吗？') === true) {
			let checkedIdObj = document.getElementById('checkedId')
			window.alert('批量折叠成功！')
			checkedIdObj.value = ids
		}
	}
}

function init() {
	let tableObj=document.getElementById("serchPost");
	let num = tableObj.rows.length
	let postlist=new Array()
	for (let i=1; i<num ;i++) {
		let row = tableObj.rows[i]
		let block = row.cells[3].innerText

		let isABlock=false
		for (let j=0; j<postlist.length;j++) {
			if(block === postlist[j]) {
				isABlock=true;
			}
		}
		if(!isABlock) {
			postlist.push(block)
		}
	}
	let blockObj=document.getElementById("selectblock")
	let trObj=document.createElement("tr")
	for(let i=0;i<postlist.length;i++) {
		let blockCheckBox=document.createElement("input")
		blockCheckBox.type="checkbox"
		blockCheckBox.name="block"
		blockCheckBox.value=postlist[i]
		blockCheckBox.innerHTML=postlist[i]
		blockCheckBox.onclick="checkobject()"
		blockCheckBox.onchange="checkobject()"
		let tdObj=document.createElement("td")
		tdObj.append(blockCheckBox)
		let temp=new String()
		temp=postlist[i]
		tdObj.append(temp)
		trObj.append(tdObj)
	}
	blockObj.append(trObj)
	let Obj = document.getElementsByName('checkbox0')
	let t = 1
	let k
	for (k in Obj) {
		Obj[k].value = tableObj.rows[t].cells[0].innerHTML
		t++
	}
}

window.onload = function () {
	init()
}
