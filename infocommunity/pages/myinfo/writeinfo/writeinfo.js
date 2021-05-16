// pages/studentInformation/studentInformation.js
let app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    files: [],
    myinfo: null,
    select: false,
    tihuoWay: '角色',
    priceList: '获得过最高奖项',
    goalList: '您的目标',
    actorTabs: ['学生', '家长', '中小学老师', '机构老师', '其他'],
    activeIndex: 0,
    priceTable: ['NOIP普及组一等奖', 'NOIP普及组二等奖', 'NOIP普及组三等奖', 'NOIP提高组一等奖', 'NOIP提高组二等奖', 'NOIP提高组三等奖', 'NOI金牌', 'NOI银牌', 'NOI铜牌', 'IOI奖项', '其他'],
    priceIndex: 0,
    goalTabs: ['了解政策', '为孩子寻找培训资料', '为孩子寻找培训机构'],
    goalIndex: 0,

    sliderOffset: 0,
    sliderLeft: -15,

    provinces: app.globalData.block,
    provinceIndex: 0,
    max: 11,
    min: 0,
    school: '',
    phoneNum: '',
    file: ''
  },
  globalData: {
    value: '',
    phoneNum: '',
    actorTabs: '',
    goalTabs: '',
    goalIndex: 0,
    priceTable: '',
    files: ''

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let stu = wx.getStorageSync('student')
    this.setData({ myinfo: stu })
    let that = this
    wx.request({
      url: app.globalData.url + '/User/searchUser',
      data: {
        wxid: app.globalData.userInfo.nickName
      },
      method: 'GET',
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        let info = res.data
        that.setData({
          info: info,
          activeIndex: info.role,
          provinceIndex: info.region

        })
        if (typeof info.img === 'undefined' || info.img == null || info.img == '') {
          that.setData({
            path: ''
          })
        } else {
          that.setData({
            path: app.globalData.url + '/image/' + info.img
          })
          console.log(that.data.path)
        }
        console.log('返回成功的数据:' + JSON.stringify(res.data))
        console.log('图片地址' + info.img)
        console.log('----------------------' + that.data.path)
      },
      fail: function (res) {
      }
    })
  },
  bindShowGoal: function () {
    this.setData({
      selectGoal: !this.data.select
    })
  },
  bindShowPri: function () {
    this.setData({
      selectPri: !this.data.select
    })
  },
  goalSelect: function (e) {
    let name = e.currentTarget.dataset.name
    this.setData({
      goalList: name,
      selectGoal: false
    })
  },
  priceSelect: function (e) {
    let name = e.currentTarget.dataset.name
    this.setData({
      priceList: name,
      selectPri: false
    })
  },
  bindCountryChange: function (e) {
    let provinceIndex = e.detail.value

    this.setData({
      provinceIndex: provinceIndex
    })
  },
  bindPrinceChange: function (e) {
    let priceIndex = e.detail.value
    this.setData({
      priceIndex: priceIndex
    })
  },
  actorChange: function (e) {
    let activeIndex = e.detail.value
    this.setData({
      activeIndex: activeIndex
    })
  },
  goalChange: function (e) {
    let goalIndex = e.detail.value
    this.setData({
      goalIndex: goalIndex
    })
  },
  parPost: function (event) {
    let phoneNum = this.data.phoneNum
    let activeIndex = this.data.activeIndex
    let goalIndex = this.data.goalIndex
    let block = this.data.provinceIndex
    if (phoneNum.length !== 11) {
      wx.showModal({
        content: '手机号码位数必须为11位',
        showCancel: false
      })
    } else {
      wx.switchTab({
        url: '../writeinfo/writeinfo'
      })
      wx.request({
        url: app.globalData.url + '/User/addParent',

        data: {

          wxid: app.globalData.userInfo.nickName,
          block: block,
          phoneNum: phoneNum,
          activeIndex: activeIndex,
          goalIndex: goalIndex
        },
        methed: 'POST',
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          wx.showToast({
            title: '已完成',
            icon: 'success',
            duration: 3000
          })
        }
      })
    }
  },
  inpSchool: function (e) {
    let _this = this
    let value = e.detail.value

    _this.setData({
      value: value
    })
    let len = parseInt(value.length)
    if (len <= this.data.min) {
      this.setData({
        texts: '文本不能为空'
      })
    } else if (len > this.data.min) {
      this.setData({
        currentWordNumber: len // 当前字数
      })
    }
    if (len > this.data.max) {
      return
      this.setData({
        texts: '文本不能超过最高字数',
        currentWordNumber: len // 当前字数
      })
    }
    this.data.school = value
  },
  inpPhoneNum: function (e) {
    let _this = this
    let value = e.detail.value
    let len = parseInt(value.length)
    _this.setData({
      value: value
    })
    if (len > this.data.max) {
      return
      this.setData({
        texts: '文本不能超过最高字数',
        currentWordNumber: len // 当前字数
      })
    }
    this.data.phoneNum = value
  },
  chooseImage: function (e) {
    let that = this
    wx.chooseImage({
      count: 9,
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片

        let files = res.tempFilePaths
        let file = files[0]
        that.setData({
          files: that.data.files.concat(res.tempFilePaths),
          file: files[0]
        })
        return file
      }

    })
  },
  stuPost: function (e) {
    let tempFilePaths = this.data.files
    let phoneNum = this.data.phoneNum
    let activeIndex = this.data.activeIndex
    let block = this.data.provinceIndex
    let priceIndex = this.data.priceIndex
    let school = this.data.school
    if (phoneNum.length !== 11) {
      wx.showModal({
        content: '手机号码位数必须为11位',
        showCancel: false
      })
    } else {
      if (Object.keys(tempFilePaths).length !== 0) {
        for (let a = 0; a < tempFilePaths.length; a++) {
          wx.uploadFile({
            url: app.globalData.url + '/User/addStudent',
            filePath: tempFilePaths[a],
            name: 'file',
            formData: {

              wxid: app.globalData.userInfo.nickName,
              phoneNum: phoneNum,
              activeIndex: activeIndex,
              block: block,
              priceIndex: priceIndex,
              school: school

            },
            methed: 'POST',
            success: function (res) {
              wx.showToast({
                title: '已完成',
                icon: 'success',
                duration: 3000
              })
            }
          })
        }
      } else {
        wx.request({
          url: app.globalData.url + '/User/addStudent',
          data: {
            wxid: app.globalData.userInfo.nickName,
            phoneNum: phoneNum,
            activeIndex: activeIndex,
            block: block,
            priceIndex: priceIndex,
            school: school
          },
          methed: 'POST',
          header: {
            'content-type': 'application/json'
          },
          success: function (res) {
            wx.showToast({
              title: '已完成',
              icon: 'success',
              duration: 3000
            })
          },
          fail: function (res) {
          }
        })
      }
    }
  },
  previewImage: function (e) {
    file = this.data.files
    wx.previewImage({
      current: e.currentTarget.id, // 当前显示图片的http链接

      urls: this.data.files // 需要预览的图片http链接列表
    })
  },
  teaPost: function (e) {
    let tempFilePaths = this.data.files
    let phoneNum = this.data.phoneNum
    let activeIndex = this.data.activeIndex
    let block = this.data.provinceIndex
    if (phoneNum.length !== 11) {
      wx.showModal({
        content: '手机号码位数必须为11位',
        showCancel: false
      })
    } else {
      if (Object.keys(tempFilePaths).length !== 0) {
        wx.uploadFile({
          url: app.globalData.url + '/User/addTeacher',
          filePath: tempFilePaths[0],
          name: 'file',
          formData: {

            wxid: app.globalData.userInfo.nickName,
            phoneNum: phoneNum,
            activeIndex: activeIndex,
            block: block

          },
          methed: 'POST',
          success: function (res) {
            wx.showToast({
              title: '已完成',
              icon: 'success',
              duration: 3000
            })
          },
          fail: function (res) {
          }
        })
      } else {
        wx.request({
          url: app.globalData.url + '/User/addTeacher',
          data: {
            wxid: app.globalData.userInfo.nickName,
            phoneNum: phoneNum,
            activeIndex: activeIndex,
            block: block

          },
          methed: 'POST',
          header: {
            'content-type': 'application/json'
          },
          success: function (res) {
            wx.showToast({
              title: '已完成',
              icon: 'success',
              duration: 3000
            })
          },
          fail: function (res) {
          }
        })
      }
    }
  },
  otherPost: function (event) {
    let phoneNum = this.data.phoneNum
    let activeIndex = this.data.activeIndex
    let block = this.data.provinceIndex
    if (phoneNum.length !== 11) {
      wx.showModal({
        content: '手机号码位数必须为11位',
        showCancel: false
      })
    } else {
      wx.switchTab({
        url: '../writeinfo/writeinfo'
      })
      wx.request({
        url: app.globalData.url + '/User/addOther',

        data: {

          wxid: app.globalData.userInfo.nickName,
          block: block,
          phoneNum: phoneNum,
          activeIndex: activeIndex
        },
        methed: 'POST',
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          wx.showToast({
            title: '已完成',
            icon: 'success',
            duration: 3000
          })
        },
        fail: function (res) {
        }
      })
    }
  }
})
