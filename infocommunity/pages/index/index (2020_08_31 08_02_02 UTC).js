// pages/frontpage/frontpage.js
const app = getApp();

var sliderWidth = 100;// 需要设置slider的宽度，用于计算中间位置
Page({
	data: {
		block: app.globalData.block,
		blockIndex: app.globalData.blockIndex,
		sequence: app.globalData.sequence,
		sequenceIndex: app.globalData.sequenceIndex,
		ref: app.globalData.ref,

		tabs: ["24小时","推荐", "精华"],
		sliderOffset: 0,
		sliderLeft: 0,
		activeIndex: 0,

		inputShowed: false,
		inputVal: "",
	},
	/*搜索框组件 */
	showInput: function () {
		this.setData({
			inputShowed: true
		});
	},
	hideInput: function () {
		this.setData({
			inputVal: "",
			inputShowed: false
		});
	},
	clearInput: function () {
		this.setData({
			inputVal: ""
		});
	},
	inputTyping: function (e) {
		this.setData({
			inputVal: e.detail.value
		});
	},

	/*选项一二三组件 */
	onLoad: function (e) {
		let that = this;
		wx.getSystemInfo({
			success: function (res) {
				that.setData({
					sliderLeft: (res.windowWidth / that.data.tabs.length - sliderWidth) / 2,
					sliderOffset: res.windowWidth / that.data.tabs.length * that.data.activeIndex
				});
			}
		});
		wx.request({
			url: app.globalData.url + "/User/searchHot",
			data: {
			},
			methed: "GET",
			header: {
				"content-type": "application/json"
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
			url: app.globalData.url + "/User/listAdv",
			data: {
			},
			methed: "GET",
			header: {
				"content-type": "application/json"
			},
			success: function (res) {
				let adv = res.data;
				for (let i = 0; i < res.data.length; i++) {
					adv[i].img = app.globalData.url + "/image/" + adv[i].img;
				}
				that.setData({
					adv: adv,
				});

			},
			fail: function (res) {
			}
		});
	},
	tabClick: function (e) {
		let that = this;
		this.setData({
			sliderOffset: e.currentTarget.offsetLeft,
			activeIndex: e.currentTarget.id
		});
		if (e.currentTarget.id == "0") {
			wx.request({
				url: app.globalData.url + "/User/searchHot",
				data: {
				},
				methed: "GET",
				header: {
					"content-type": "application/json"
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
		} else if (e.currentTarget.id == "1") {
			that.data.ref = 0;
			app.postRequest(e, that);
		} else {
			that.data.ref = 1;
			app.postRequest(e, that);
		}
	},
  
	//排序
	bindPickerSequenceChange: function (e) {
		if (this.data.activeIndex != 0) {
			let that = this;
			this.setData({
				sequenceIndex: e.detail.value
			});
			app.postRequest(e, that);
		}
	},

	// 跳转到content界面
	clickIntoContent: function (e) {
		let that = this;
		let postIndex = e.currentTarget.dataset.index;
		let pid = this.data.info[postIndex].pid;
		wx.navigateTo({
			url: "../content/content?pid=" + this.data.info[postIndex].pid
		});
	},

	searchVague: function (e) {
		let that = this;
		wx.request({
			url: app.globalData.url + "/User/searchVague",
			data: {
				title: this.data.inputVal,
			},
			methed: "GET",
			header: {
				"content-type": "application/json"
			},
			success: function (res) {
				let result = res.data;
				that.setData({
					result: result
				});
			},
			fail: function (res) {
			}
		});
	}
});
