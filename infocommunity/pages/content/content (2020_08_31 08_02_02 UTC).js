// pages/content/content.js
let app = getApp();

Page({

	/**
   * 页面的初始数据
   */

	data: {
		files: [],
		min: 0,
		max: 200,
		pid: 0
	},
	globalData: {
		value: "",
		content: ""
	},

	onLoad: function (event) {
		let pid = event.pid;
		this.setData({
			pid: pid 
		});
		let that = this;
		wx.request({
			url: app.globalData.url + "/User/searchPostByPid",
			data: {
				pid: pid
			},

			method: "GET",
			header: {
				"content-type": "application/json" // 默认值
			},
			success: function (res) {
				let info = res.data;
				that.setData({
					info: info
				});
			},
			fail: function (res) {
			}
		});
		wx.request({
			url: app.globalData.url + "/User/searchCommentByPid",
			data: {
				pid: pid
			},

			method: "GET",
			header: {
				"content-type": "application/json" // 默认值
			},
			success: function (res) {
        console.log(res.data);
				let info2 = res.data;
				that.setData({
					info2: info2
				});
			},
			fail: function (res) {
			}
		});
	},

	inpContent: function (e) {
		let _this = this;
		let value = e.detail.value;

		_this.setData({
			value: value
		});
		let len = parseInt(value.length);
		if (len <= this.data.min) {
			this.setData({
				texts: "文本不能为空"
			});
		} else if (len > this.data.min) {
			this.setData({
				currentWordNumber: len // 当前字数
			});
		}
		if (len > this.data.max) {
			return;
			this.setData({
				texts: "文本不能超过最高字数",
				currentWordNumber: len // 当前字数
			});
		}
		this.data.content = value;
	},

	myPost: function (event) {
		let content = this.data.content;
		wx.redirectTo({
			url: "../content/content?pid=" + this.data.pid
		});
		wx.request({
			url: app.globalData.url + "/User/addComment",

			data: {
				wxid: app.globalData.userInfo.nickName,
				commentContent: content,
				pid: this.data.pid
			},
			methed: "GET",
			header: {
				"content-type": "application/json"
			},
			success: function (res) {
				if(res.data == undefined) {
          
					wx.redirectTo({
						url: "../myinfo/writeinfo/writeinfo"
					});
				}
			},
			fail: function (res) {

			}
		});
	},
	clickPriase: function (e) {
		wx.request({
			url: app.globalData.url + "/User/makeChoice",

			data: {
				wxid: app.globalData.userInfo.nickName,
				pid: this.data.pid,
				type: 1
			},
			methed: "GET",
			header: {
				"content-type": "application/json"
			},
			success: function (res) {
			},
			fail: function (res) {
			}
		});
		wx.redirectTo({
			url: "../content/content?pid=" + this.data.pid
		});
	},
	clickCriticism: function (e) {
		wx.request({
			url: app.globalData.url + "/User/makeChoice",

			data: {
				wxid: app.globalData.userInfo.nickName,
				pid: this.data.pid,
				type: 0
			},
			methed: "GET",
			header: {
				"content-type": "application/json"
			},
			success: function (res) {
			},
			fail: function (res) {
			}
		});

		wx.redirectTo({
			url: "../content/content?pid=" + this.data.pid
		});
	},
	searchUser: function (e) {
		wx.request({
			url: app.globalData.url + "/User/searchUser",
			data: {
				wxid: app.globalData.userInfo.nickName
			},
			method: "GET",
			header: {
				"content-type": "application/json" // 默认值
			},
			success: function (res) {
				if (res.data.wxid == null) {
					wx.redirectTo({
						url: "../myinfo/writeinfo/writeinfo"
					});
				}
			},
			fail: function (res) {
			}
		});
	}
});