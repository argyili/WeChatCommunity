// pages/message/mycomment/mycomment.js
Page({
	/**
   * 页面的初始数据
   */
	data: {
	},
	/**
   * 生命周期函数--监听页面加载
   */
	onLoad: function (options) {
		let commentcontent = options.commentcontent;
		this.setData({
			commentcontent: commentcontent
		});
	}
});
