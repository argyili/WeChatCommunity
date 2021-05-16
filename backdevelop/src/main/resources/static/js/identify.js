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

function View (wxid) {
    let mIdObj1 = document.getElementById('wxid')
    mIdObj1.value = wxid
    let mIdObj2 = document.getElementById('identification')
    mIdObj2.value = 2
}

function init () {
    goPage(1, 10)
    let itable = document.getElementById('id_data')
    let num = itable.rows.length
    for (let i = 1; i < num; i++) {
        let row = itable.rows[i]
        let wxid = row.cells[0].innerText
        let td1 = document.createElement('td')
        td1.class="view"
        let button1 = document.createElement('button')
        button1.type = 'submit'
        button1.innerText = '查看'
        button1.value = wxid
        button1.onclick = function () {
            let newEventText = this.value
            View(newEventText)
        }
        td1.append(button1)
        row.append(td1)
        let forbidden = row.cells[3].innerText
        let forbiddenString = ['未禁言','禁言']
        row.cells[3].innerText = forbiddenString[forbidden]
    }
    var mIdObj = document.getElementById('wxid')
    mIdObj.value = ''
}

window.onload = function () {
    init()
}
