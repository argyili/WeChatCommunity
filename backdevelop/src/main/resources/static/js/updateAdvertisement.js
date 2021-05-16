function insertpic () {
	let pathObj = document.getElementById('img2')
	path = pathObj.value
	document.getElementById('myimage').innerHTML = '<img src="' + path + '" />'
}
let xhr

function UpladFile () {
	document.getElementById('progress_bar').style.display = ''
	document.getElementById('percentage').style.display = ''

	let fileObj = document.getElementById('file').files[0]
	let url = 'http://www.mylifeview.cn:8081' + '/superAdmin/addadvertisement'

	let form = new FormData()
	form.append('file', fileObj)

	xhr = new XMLHttpRequest()
	xhr.open('post', url, true)
	xhr.onload = uploadComplete
	xhr.onerror = uploadFailed

	xhr.upload.onprogress = progressFunction
	xhr.upload.onloadstart = function () {
		let ot = new Date().getTime()
		let oloaded = 0
	}

	xhr.send(form)
	let imgfileObj = document.getElementById('newImg')
	imgfileObj.value = fileObj.name
}

function uploadComplete (evt) {
	let data = JSON.parse(evt.target.responseText)
	if (data.success) {
		alert('上传成功！')
	} else {
		alert('上传失败！')
		let imgfileObj = document.getElementById('newImg')
		imgfileObj.value = ''
	}
}

function uploadFailed (evt) {
	alert('上传失败！')
	let imgfileObj = document.getElementById('newImg')
	imgfileObj.value = ''
}

function progressFunction (evt) {
	let progressBar = document.getElementById('progress_bar')
	let percentageDiv = document.getElementById('percentage')
	if (evt.lengthComputable) { //
		progressBar.max = evt.total
		progressBar.value = evt.loaded
		percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + '%'
	}
	let time = document.getElementById('time')
	let nt = new Date().getTime()
	let pertime = (nt - ot) / 1000
	ot = new Date().getTime()
	let perload = evt.loaded - oloaded
	oloaded = evt.loaded
	let speed = perload / pertime
	let bspeed = speed
	let units = 'b/s'
	if (speed / 1024 > 1) {
		speed = speed / 1024
		units = 'k/s'
	}
	if (speed / 1024 > 1) {
		speed = speed / 1024
		units = 'M/s'
	}
	speed = speed.toFixed(1)
	let resttime = ((evt.total - evt.loaded) / bspeed).toFixed(1)
	time.innerHTML = '，速度：' + speed + units + '，剩余时间：' + resttime + 's'
	if (bspeed === 0) {time.innerHTML = '上传已取消'}
}

function init () {
	insertpic()
	document.getElementById('progress_bar').style.display = 'none'
	document.getElementById('percentage').style.display = 'none'
}

window.onload = function () {
	init()
}
