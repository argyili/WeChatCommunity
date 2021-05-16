//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo
              wx.request({
                url: this.globalData.url + "/User/achieveInfoNum",
                data: {
                  wxid: this.globalData.userInfo.nickName
                },
                method: "GET",
                header: {
                  "content-type": "application/json" // 默认值
                },
                success: function (res) {
                  let achieveInfoNum = res.data;
                  if (achieveInfoNum !== '0') {
                    wx.setTabBarBadge({
                      index: 3,
                      text: achieveInfoNum
                    })
                  }
                },
                fail: function (res) {
                  console.log(".....fail.....");
                }
              }) 
              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })
  },
  globalData: {
    userInfo: null,
    block: ["全国", "安徽", "北京", "重庆", "福建", "甘肃", "广东", "广西", "贵州", "海南", "河北", "河南", "黑龙江", "湖北", "湖南", "吉林", "江苏", "江西", "辽宁", "内蒙古", "宁夏", "青海", "山东", "山西", "陕西", "上海", "四川", "天津", "西藏", "新疆", "云南", "浙江", "香港", "澳门", "台湾"],
    blockIndex: 0,
    sequence: ["综合排序", "点赞排序", "点踩排序", "评论排序"],
    sequenceIndex: 0,
    ref: 0,
    url: "http://localhost:8081"
  },
  
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
  test01: function (event, that) {
    that.data.page = "修改后的page数据";
    that.setData({
      page: that.data.page
    })
  },
  postRequest: function (e, that) {
    wx.request({
      url: this.globalData.url + "/User/searchPostBy",
      data: {
        block: that.data.block[that.data.blockIndex],
        sequence: that.data.sequence[that.data.sequenceIndex],
        ref: that.data.ref
      },
      method: "GET",
      header: {
        "content-type": "application/json" // 默认值
      },
      success: function (res) {
        console.log(res.data);
        let info = res.data;
        that.setData({
          info: info
        });
      },
      fail: function (res) {
        console.log(".....fail.....");
      }
    })
  },
})