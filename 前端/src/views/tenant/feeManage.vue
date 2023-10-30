<template>
  <div style="width:100%">
    <el-row>
      <el-button style="float: left; padding: 3px 0;color: #20a0ff;" type="text" @click="openVip = true">开通会员
      </el-button>
    </el-row>
    <el-row :gutter="50">
      <el-col :span="8">
        <el-card @click.native="showInfo(0)">
          <div slot="header" class="clearfix">
            <span>群组使用情况</span>
            <el-button style="float: right; padding: 3px 0;color: #20a0ff;" type="text" @click="buy('group')">购买
            </el-button>
          </div>
          <el-progress type="circle" :stroke-width="8" :percentage="(group[0] / group[1] * 100).toFixed(2)">
          </el-progress>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card @click.native="showInfo(1)">
          <div slot="header" class="clearfix">
            <span>问卷使用情况</span>
            <el-button style="float: right; padding: 3px 0;color: #20a0ff;" type="text" @click="buy('questions')">购买
            </el-button>
          </div>
          <el-progress type="circle" :stroke-width="8" :percentage="(questions[0] / questions[1] * 100).toFixed(2)">
          </el-progress>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card @click.native="showInfo(2)">
          <div slot="header" class="clearfix">
            <span>答题次数使用情况</span>
            <el-button style="float: right; padding: 3px 0;color: #20a0ff;" type="text" @click="buy('anwser')">购买
            </el-button>
          </div>
          <el-progress type="circle" :stroke-width="8" :percentage="(users[0] / users[1] * 100).toFixed(2)">
          </el-progress>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="50" style="margin-top:30px">
      <el-col :span="12">
        <div class="box-class">

          <el-card>
            <div id="demoChart" style="background-color: rgb(255 255 255);"></div>
          </el-card>
        </div>
      </el-col>
      <el-col :span="12">
        <div class="box-class">
          <el-card>
            <div id="forUsers" style="background-color: rgb(255 255 255);"></div>
          </el-card>
        </div>
      </el-col>
    </el-row>

    <el-dialog :title="buyTitle" :visible.sync="dialogFormVisible" width="50%">
      <el-form :model="buyForm" label-position="left" ref="questionerForm">
        <el-form-item label="单价" label-width="100px" prop="unitPrice">
          <el-input-number :value="unitPrice" controls-position="right" disabled></el-input-number>
        </el-form-item>
        <el-form-item label="购买数量" label-width="100px" prop="name">
          <el-input-number v-model="buyForm.num" :min="1" label="请输入购买数量" controls-position="right"></el-input-number>
        </el-form-item>
        <el-form-item label="总价" label-width="100px" prop="name">
          <el-input-number :value="buyForm.num * unitPrice" :min="1" disabled controls-position="right">
          </el-input-number>
        </el-form-item>
        <el-form-item label="套餐" label-width="100px" prop="taocan">
          <el-select v-model="buyForm.taocan" placeholder="请选择套餐" v-if="buyTitle == '购买群组'">
            <el-option label="套餐一：20元" value="1"><span style="float: left">套餐一：20元</span>
              <span style="float: right; color: #8492a6; font-size: 10px">购买10群组 赠送10答题次数</span>
            </el-option>
            <el-option label="套餐二：40元" value="2"><span style="float: left">套餐二：40元</span>
              <span style="float: right; color: #8492a6; font-size: 10px">购买20群组 赠送5问卷+10答题次数</span>
            </el-option>
            <el-option label="套餐三：60元" value="3"><span style="float: left">套餐三：60元</span>
              <span style="float: right; color: #8492a6; font-size: 10px">购买30群组 赠送10问卷+25答题次数</span>
            </el-option>
          </el-select>
          <el-select v-model="buyForm.taocan" placeholder="请选择套餐" v-if="buyTitle == '购买答卷'">
            <el-option label="套餐一：20元" value="1"><span style="float: left">套餐一：20元</span>
              <span style="float: right; color: #8492a6; font-size: 10px">购买20答卷 赠送30答题次数</span>
            </el-option>
            <el-option label="套餐二：40元" value="2"><span style="float: left">套餐二：40元</span>
              <span style="float: right; color: #8492a6; font-size: 10px">购买40答卷 赠送50答题次数+5群组</span>
            </el-option>
            <el-option label="套餐三：60元" value="3"><span style="float: left">套餐三：60元</span>
              <span style="float: right; color: #8492a6; font-size: 10px">购买60答卷 赠送80答题次数+10群组</span>
            </el-option>
          </el-select>
          <el-select v-model="buyForm.taocan" placeholder="请选择套餐" v-if="buyTitle == '购买答题次数'">
            <el-option label="套餐一：20元" value="1"><span style="float: left">套餐一：20元</span>
              <span style="float: right; color: #8492a6; font-size: 10px">购买40答题次数 赠送5答卷</span>
            </el-option>
            <el-option label="套餐二：40元" value="2"><span style="float: left">套餐二：40元</span>
              <span style="float: right; color: #8492a6; font-size: 10px">购买80答题次数 赠送10答卷+5答题次数</span>
            </el-option>
            <el-option label="套餐三：60元" value="3"><span style="float: left">套餐三：60元</span>
              <span style="float: right; color: #8492a6; font-size: 10px">购买120答题次数 赠送15答卷+10答题次数</span>
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="buyItem()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="开通会员" :visible.sync="openVip" width="50%">
      <el-container>
        <el-aside style="height:100%">
          <el-card>
            <div slot="header" class="clearfix">
              <h2>问卷通会员</h2>
              <span>电脑/手机 会员通用</span>
            </div>
            <div>
              会员特权
            </div>
          </el-card>
        </el-aside>
        <el-main>
          <el-row :gutter="10">
            <el-col :span="8">
              <el-card>
                <div slot="header">
                  <span>12个月</span>
                </div>
                <div>
                  <span>￥89</span>
                </div>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card>
                <div slot="header">
                  <span>连续包月</span>
                </div>
                <div>
                  <span>￥79</span>
                </div>

              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card>
                <div slot="header">
                  <span>连续包年</span>
                </div>
                <div>
                  <span>￥12</span>
                </div>

              </el-card>
            </el-col>
          </el-row>
          <el-row>
            <el-button type="primary"> 立即支付</el-button>
          </el-row>
        </el-main>
      </el-container>
    </el-dialog>
  </div>
</template>
<script>
import resize from '@/utils/resize'
export default {
  mixins: [resize],
  data() {
    return {
      buyingItem: '',
      openVip: false,
      buyTitle: '',
      dialogFormVisible: false,
      buyForm: {},
      group: [0, 1],
      users: [0, 1],
      questions: [0, 1],

      chart: null,
      usersChart: null,
      userChartOption: {
        legend: {
          // icon: 'circle',
          itemWidth: 15,
          itemHeight: 8,
          itemGap: 8,
          align: "left", // icon 位于文字位置
          data: ['群组', '答卷', '答者'],
          textStyle: {
            fontSize: 15
          },
          left: 0,
          top: 0
        },
        toolbox: {
          show: true,
          feature: {
            dataView: { show: true, readOnly: false },
            magicType: { show: true, type: ['line', 'bar'] },
            restore: { show: true },
            saveAsImage: { show: true }
          },
          top: 0,
          itemSize: 15,//工具栏 icon 的大小
        },
        xAxis: [
          {
            type: 'category',
            data: ['用户1', '用户2', '用户3', '用户4'],
            axisTick: {
              alignWithLabel: true,
            },
            nameTextStyle: {
              color: '#82b0ec',
            },
            axisLine: {
              lineStyle: {
                color: '#82b0ec',
              },
            },
            axisLabel: {
              textStyle: {
                fontSize: 15
              },
            },
          },
        ],
        dataZoom: [//滑动条
          {
            type: 'inside',
            realTime: true,
            roam: true,
            startValue: 0, // 从头开始。
            endValue: 7  // 一次性展示6个。
          }
        ],
        yAxis: [{
          type: 'value',
          axisLabel: {
            textStyle: {
              fontSize: 15
            },
          },
          axisLine: {
            show: false,
          },
          //网格样式
          splitLine: {
            show: true,
            lineStyle: {
              color: 'rgba(125, 155, 243, .2)',
              width: .5,
              type: 'solid'
            }
          },
        }],
        grid: {
          left: 10,
          right: 10,
          bottom: 10,
          top: 25,
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'line'
          },
          padding: [5, 10],
        },
        series: [
          {
            name: '群组',
            type: 'bar',
            symbolSize: [20, 10],
            symbolOffset: [0, -5],
            symbolPosition: 'end',
            z: 12,
            label: {
              normal: {
                show: true,
                position: 'top',
                color: '#fff',
                fontSize: 10
              },
            },
            data: [70, 80, 90, 100],
          },
          {
            name: '答者',
            type: 'bar',
            symbolSize: [20, 10],
            symbolOffset: [0, 5],
            z: 12,
            data: [50, 20, 30, 40],
          },
          {
            name: '答卷',
            type: 'bar',
            itemStyle: {
              normal: {
                opacity: 0.7,
              },
            },
            barWidth: '20',
            data: [0, 30, 40, 50],
          },
        ]
      },
      chartOption: {
        color: ['#24CBF1', '#868EF9', '#E6A23C'],
        legend: {
          // icon: 'circle',
          itemWidth: 15,
          itemHeight: 8,
          itemGap: 8,
          align: "left", // icon 位于文字位置
          data: ['群组', '答卷', '答者'],
          textStyle: {
            fontSize: 15
          },
          left: 0,
          top: 0
        },
        toolbox: {
          show: true,
          feature: {
            dataView: { show: true, readOnly: false },
            magicType: { show: true, type: ['line', 'bar'] },
            restore: { show: true },
            saveAsImage: { show: true }
          },
          top: 15,
          itemSize: 15,//工具栏 icon 的大小
          iconStyle: {
            // borderColor: '#fff',
          }
        },
        xAxis: [{
          type: 'category',
          data: ['2022-03', '2022-04', '2022-05', '2022-06', '2022-07', '2022-08', '2022-09'],
          boundaryGap: true,
          axisTick: {
            show: false
          },
          axisLabel: {
            rotate: 30,
            textStyle: {
              fontSize: 15
            }
          },
          //网格样式
          splitLine: {
            show: true,
            lineStyle: {
              color: 'rgba(125, 155, 243, .2)',
              width: .5,
              type: 'solid'
            }
          },
        }],
        dataZoom: [//滑动条
          {
            type: 'inside',
            realTime: true,
            roam: true,
            startValue: 0, // 从头开始。
            endValue: 7  // 一次性展示6个。
          }
        ],
        yAxis: [{
          type: 'value',
          name: '单位/件',
          minInterval: 5,
          min: 0,
          nameTextStyle: {
            fontSize: 15,
          },
          axisLabel: {//y轴字体样式
            rotate: 60,
            interval: 0,
            textStyle: {
              fontSize: 15
            }
          },
          axisLine: {
            show: true, //隐藏y坐标轴
          },
          //网格样式
          splitLine: {
            show: true,
            lineStyle: {
              color: 'rgba(125, 155, 243, .2)',
              width: .5,
              type: 'solid'
            }
          },
        }],
        grid: {
          left: 0,
          right: 10,
          bottom: 0,
          top: 60,
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'line'
          },
          padding: [5, 10],
        },
        series: [
          {
            name: '群组',
            type: 'line',
            stack: '群组',
            smooth: true,  //这个是把线变成曲线
            areaStyle: {}, //填充区域样式
            data: [100, 150, 300, 400, 700, 500]
          },
          {
            name: '答卷',
            type: 'line',
            stack: '答卷',
            smooth: true,  //这个是把线变成曲线
            areaStyle: {}, //填充区域样式
            data: [200, 100, 500, 600, 451]
          },
          {
            name: '答者',
            type: 'line',
            stack: '答者',
            smooth: true,  //这个是把线变成曲线
            areaStyle: {}, //填充区域样式
            data: [300, 50, 544, 122, 666]
          },
        ]
      }
    }
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  mounted() {
    this.group = [Math.ceil(Math.random() * 1000 + 1), Math.ceil(Math.random() * 10000 + 1)]
    this.users = [Math.ceil(Math.random() * 1000 + 1), Math.ceil(Math.random() * 10000 + 1)]
    this.questions = [Math.ceil(Math.random() * 1000 + 1), Math.ceil(Math.random() * 10000 + 1)]
    this.$nextTick(() => {
      this.initChart()
      this.initUsersChart()
      window.addEventListener("resize", () => {
        this.chart.resize()
        this.usersChart.resize()
      })
    })
  },
  methods: {
    initUsersChart() {
      this.usersChart = this.$echarts.init(document.getElementById('forUsers'))
      this.setOptions(this.usersChart, this.userChartOption)
    },
    initChart() {
      this.chart = this.$echarts.init(document.getElementById('demoChart'))
      this.setOptions(this.chart, this.chartOption)
    },
    setOptions(chart, options) {
      chart.setOption(options)
    },
    showInfo(op) {
      switch (op) {
        case 0:
          this.chartOption.legend.selected = { '群组': true, '答卷': false, '答者': false };
          this.userChartOption.legend.selected = { '群组': true, '答卷': false, '答者': false }; break;
        case 1:
          this.chartOption.legend.selected = { '群组': false, '答卷': true, '答者': false };
          this.userChartOption.legend.selected = { '群组': false, '答卷': true, '答者': false }; break;
        case 2:
          this.chartOption.legend.selected = { '群组': false, '答卷': false, '答者': true };
          this.userChartOption.legend.selected = { '群组': false, '答卷': false, '答者': true }; break;
      }
      this.initChart();
      this.initUsersChart();
    },
    buy(item) {
      this.buyingItem = item;
      switch (item) {
        case 'group':
          this.buyTitle = '购买群组'
          break;
        case 'anwser':
          this.buyTitle = '购买答题次数'
          break;
        case 'questions':
          this.buyTitle = '购买答卷'
          break;
      }
      this.dialogFormVisible = true
    },
    buyItem() {
      this.$post('/api/tenant/buyItem', {
        item: this.buyingItem,
        num: this.buyForm.num
      }).then(() => {
        this.$message.success('购买成功')
        this.dialogFormVisible = false
      })
    }
  },
  computed: {
    unitPrice() {
      switch (this.buyTitle) {
        case '购买群组':
          return 2
        case '购买答题次数':
          return 1
        case '购买答卷':
          return 0.5
      }
    }
  }
}
</script>
<style>
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

#demoChart {
  height: 500px;
  margin-left: 25px
}

#forUsers {
  height: 500px;
  margin-left: 25px
}

.box-class {
  background: #f1f1f1;
  height: calc(33.3%);
}
</style>
