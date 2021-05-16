// pages/frontpage/frontpage.js
const app = getApp();

Page({
	data: {
		block: app.globalData.block,
		blockIndex: app.globalData.blockIndex+1,
		sequence: app.globalData.sequence,
		sequenceIndex: app.globalData.sequenceIndex,
		ref: app.globalData.ref,

		tabs: ["省份热门", "省份精华"],
		sliderOffset: 0,
		sliderLeft: 0,
		activeIndex: 0,

		inputShowed: false,
		inputVal: ""
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
		app.postRequest(e, this);
		let that = this;
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
					adv: adv
				});
			},
			fail: function (res) {
			}
		});
	},

	tabClick: function (e) {
		let that = this;
		this.setData({
			activeIndex: e.currentTarget.id
		});
		//若点击省份精华
		if (e.currentTarget.id == "1") {
			that.data.ref = 1;
			app.postRequest(e, that);
		} else {
			//若点击省份热门
			that.data.ref = 0;
			app.postRequest(e, that);

		}
	},
  
	//区域板块
	bindPickerBlockChange: function (e) {
		let that = this;
		this.setData({
			blockIndex: e.detail.value
		});
		app.postRequest(e, that);
	},

	//排序
	bindPickerSequenceChange: function (e) {
		let that = this;
		this.setData({
			sequenceIndex: e.detail.value
		});
		app.postRequest(e, that);
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
