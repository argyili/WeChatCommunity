function init () {
    let forbidden = ['未禁言','禁言']
	let forbiddenIndex = document.getElementById('forbidden').innerText
	document.getElementById('forbidden').innerText = forbidden[forbiddenIndex]
    let identification = document.getElementById("identification")
	let pass = document.getElementById("pass")
	pass.onclick = function () {
	    identification.value = 1
    }
    let notPass = document.getElementById("notPass")
    notPass.onclick = function () {
		identification.value = 0
    }
    let topPrize = ['NOIP普及组一等奖', 'NOIP普及组二等奖', 'NOIP普及组三等奖', 'NOIP提高组一等奖', 'NOIP提高组二等奖', 'NOIP提高组三等奖', 'NOI金牌', 'NOI银牌', 'NOI铜牌', 'IOI奖项', '其他']
    let topPrizeIndex = document.getElementById('topPrize').innerText
    document.getElementById('topPrize').innerText = topPrize[topPrizeIndex]
}

window.onload = function () {
	init()
}
