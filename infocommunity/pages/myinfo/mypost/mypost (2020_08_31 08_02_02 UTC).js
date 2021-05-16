// pages/myinfo/mypost/mypost.js
const app = getApp();

Page({
	data: {
	},

	onLoad: function () {
		let that = this;
		wx.request({
			url: app.globalData.url + "/User/searchByWxid",
			data: {
				wxid: app.globalData.userInfo.nickName,
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
	},

	// 跳转到content界面
	clickIntoContent: function (e) {
		let that = this;
		let postIndex = e.currentTarget.dataset.index;
		let pid = this.data.info[postIndex].pid;
		wx.navigateTo({
			url: "../../content/content?pid=" + this.data.info[postIndex].pid
		});
	}
});