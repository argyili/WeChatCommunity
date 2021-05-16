// pages/message/message.js
let mycomment;
let tomecomment;
var sliderWidth = 85;
const app = getApp();
Page({
	/**
   * 页面的初始数据
   */
	data: {
		tabs: ["收到消息", "收到赞踩", "我的评论"],
		activeIndex: 2,
		sliderOffset: 0,
		sliderLeft: 15,
		mycomment: null
	},
	onLoad: function () {
		let that = this;
		wx.request({
			url: app.globalData.url + "/User/commentByMyselef",
			data: {
				wxid: app.globalData.userInfo.nickName
			},
			method: "GET",
			header: {
				"content-type": "application/json" // 默认值
			},
			success: function (res) {
				mycomment = res.data;
				that.setData({
					mycomment: mycomment
				});
			},
			fail: function (res) {
			}
		}),
		wx.request({
			url: app.globalData.url + "/User/commentToMe",
			data: {
				wxid: app.globalData.userInfo.nickName
			},
			method: "GET",
			header: {
				"content-type": "application/json" // 默认值
			},
			success: function (res) {
				let tomecomment = res.data;
				that.setData({
					tomecomment: tomecomment
				});
			},
			fail: function (res) {
			}
		}),
		wx.request({
			url: app.globalData.url + "/User/commentToMeNickname",
			data: {
				wxid: app.globalData.userInfo.nickName
			},
			method: "GET",
			header: {
				"content-type": "application/json" // 默认值
			},
			success: function (res) {
				console.log(res.data);
				let nickname = res.data;
				that.setData({
					nickname: nickname
				});
			},
			fail: function (res) {
				console.log(".....fail.....");
			}
		}),
		wx.request({
			url: app.globalData.url + "/User/searchChoiceToMe",
			data: {
				wxid: app.globalData.userInfo.nickName
			},
			method: "GET",
			header: {
				"content-type": "application/json" // 默认值
			},
			success: function (res) {
				console.log(res.data);
				let choice = res.data;
				that.setData({
					choice: choice
				});
			},
			fail: function (res) {
				console.log(".....fail.....");
			}
		}),
		wx.getSystemInfo({
			success: function (res) {
				that.setData({
					sliderLeft: (res.windowWidth / that.data.tabs.length - sliderWidth) / 2,
					sliderOffset: res.windowWidth / that.data.tabs.length * that.data.activeIndex
				});
			}
		}),
		wx.removeTabBarBadge({
			index: 3
		}),
		that.setData({
		});
	},
	tabClick: function (e) {
		this.setData({
			sliderOffset: e.currentTarget.offsetLeft,
			activeIndex: e.currentTarget.id
		});
	},
	jumpMycomment: function (e) {
		var index = e.currentTarget.dataset.index;
		console.log("index" + index);
		var commentcontent = mycomment[index].commentcontent;
		wx.navigateTo({
			url: "../message/mycomment/mycomment?commentcontent=" + commentcontent
		});
	}
});
