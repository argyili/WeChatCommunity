let app = getApp();
Page({

	/**
   * 页面的初始数据
   */

	data: {
		files: [],
		min: 0,
		max: 200,
		provinces: app.globalData.block,
		provinceIndex: 0,

	},
	globalData: {
		value: "",
		title: "",
		content: "",
		block:""

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
	inpTitle: function (e) {
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
		this.data.title = value;
	},
	getUserInfo: function (e) {
		app.globalData.userInfo = e.detail.userInfo;
		this.setData({
			userInfo: e.detail.userInfo,
			nickName: e.userInfo.nickName,
			hasUserInfo: true,
			title: e.title,
			text: e.text,
			files: e.files
		});
	},
	// 连接后台
	myPost: function (event) {
		let title = this.data.title;
		let content = this.data.content;
		let block = this.data.block;
		wx.switchTab({
			url: "../index/index"
		});
		wx.request({
			url: app.globalData.url + "/User/addPost",

			data: {

				wxid: app.globalData.userInfo.nickName,
				block: block,
				content: content,
				title: title

			},
			methed: "POST",
			header: {
				"content-type": "application/json"
			},
			success: function (res) {

			},
			fail: function (res) {
			}
		});
	},
	tabClick: function (e) {
		this.setData({
			sliderOffset: e.currentTarget.offsetLeft,
			activeIndex: e.currentTarget.id
		});
	},
	bindCountryChange: function (e) {
		let provinceIndex = e.detail.value;
		this.setData({
			provinceIndex: provinceIndex
		});
		this.data.block = this.data.provinces[provinceIndex];
	}
});
