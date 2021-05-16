function changeChoice (choice) {
	let inputBody = document.getElementById('choice')
	inputBody.value = choice
	let hasBody = document.getElementById('has')
	let has = hasBody.value
	if (has === '-2') {
		if (choice === '是') {
			let regionObject = document.getElementsByName('region')
			let length = 0
			let k
			for (k in regionObject) {
				if (regionObject[k].checked) {
					length++
				}
			}
			if (length === 0) {
				let tempObject = document.getElementById('default_region')
				tempObject.checked = 'checked'
			}
			var phoneBody = document.getElementById('userPhone')
			phoneBody.disabled = ''
		} else {
			var phoneBody = document.getElementById('userPhone')
			phoneBody.disabled = ''
			document.getElementById('t0').style.display = 'none'
		}
	}
}

function init () {
	let hasBody = document.getElementById('has')
	let has = hasBody.value
	if (has === '-2') {
		let phoneBody = document.getElementById('userPhone')
		phoneBody.disabled = 'disabled'
	}
}

window.onload = function () {
	init()
}
