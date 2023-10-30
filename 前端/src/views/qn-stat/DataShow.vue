<template>
  <div class="Commit">
    <el-card class="question">
      <el-row>
        <el-col :span="12">
          <div>
            <div class="el-statistic">
              <div class="head">
                <span class="title">最近一次更新</span>
              </div>
              <div class="con">
                <span class="prefix">
                  <i class="el-icon-s-flag" style="color: red;"></i>
                </span>
                <span class="number">{{ nowDate }}</span>
                <span class="suffix"></span>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="12">
          <div>
            <div class="el-statistic">
              <div class="head">
                <span class="title">答卷总数</span>
              </div>
              <div class="con">
                <span class="prefix">
                  <i class="el-icon-s-flag" style="color: red;"></i>
                </span>
                <span class="number">{{ 120 }}</span>
                <span class="suffix">
                  <i class="el-icon-view" style="color: blue;"></i>
                </span>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <div>
          <China
            :area-code.sync="areaCode"
            :area-level.sync="areaLevel"
            :area-name.sync="areaName"
            :map-name-list.sync="mapNameList"
            :map-code-list.sync="mapCodeList"
            :area-statistic="areaStatistic"
            style="height: 500px;width:100%"
          ></China>
        </div>
      </el-row>
      <el-row :guter="15" v-for="(item, index) in detail" v-if="index % 2 == 0" :key="index">
        <el-col :span="12">
          <el-card>
            <div slot="header" class="clearfix">
              <span>{{ index + 1 + "." + item.title }}</span>
              <el-dropdown>
                <span class="el-dropdown-link">
                  <i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown" v-if="item.type != 'text'" type="primary">
                  <el-dropdown-item @click.native="changeValue(index, 1)">柱状图</el-dropdown-item>
                  <el-dropdown-item @click.native="changeValue(index, 2)">饼状图</el-dropdown-item>
                  <el-dropdown-item @click.native="changeValue(index, 3)">圆环图</el-dropdown-item>
                  <el-dropdown-item @click.native="changeValue(index, 4)">条形图</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
            <!--如果数据库中的问题类型为单项选择或者多项选择-->
            <!--则将数据库中的数据以表格、柱状图、饼状图、圆环图、条形图的方式进行展示-->
            <div v-if="item.type == 'radio' || item.type == 'checkbox'">
              <div :id="'chartBar' + index" class="img"></div>
            </div>
            <!--如果数据库中的问题类型为text类型则将数据以弹窗表格的形式进行显示-->
            <div v-if="item.type == 'text'">
              <!-- <el-button size="mini" type="primary" plain @click.native="lookTextDetail(item.indexId)">
                                详细内容
                            </el-button> -->
              <div :id="'chartBar' + index" class="cloud"></div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="12" v-if="index + 1 < detail.length">
          <el-card>
            <div slot="header" class="clearfix">
              <span>{{ index + 2 + "." + detail[index + 1].title }}</span>
              <el-dropdown v-if="detail[index + 1].type != 'text'">
                <span class=" el-dropdown-link">
                  <i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown" type="primary">
                  <el-dropdown-item @click.native="changeValue(index + 1, 1)">柱状图</el-dropdown-item>
                  <el-dropdown-item @click.native="changeValue(index + 1, 2)">饼状图</el-dropdown-item>
                  <el-dropdown-item @click.native="changeValue(index + 1, 3)">圆环图</el-dropdown-item>
                  <el-dropdown-item @click.native="changeValue(index + 1, 4)">条形图</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
            <!--如果数据库中的问题类型为单项选择或者多项选择-->
            <!--则将数据库中的数据以表格、柱状图、饼状图、圆环图、条形图的方式进行展示-->
            <div v-if="detail[index + 1].type == 'radio' || detail[index + 1].type == 'checkbox'">
              <div :id="'chartBar' + (index + 1)" class="img"></div>
            </div>
            <!--如果数据库中的问题类型为text类型则将数据以弹窗表格的形式进行显示-->
            <div v-if="detail[index + 1].type == 'text'">
              <!-- <el-button size="mini" type="primary" plain
                                @click.native="lookTextDetail(detail[index + 1].questionId)">
                                详细内容
                            </el-button> -->
              <div :id="'chartBar' + (index + 1)" class="cloud"></div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import China from '@/components/map/china.vue'
import { designOpera, analysis } from "../../utils/api";
const format = (dat) => {
    //获取年月日，时间
    var year = dat.getFullYear();
    var mon = (dat.getMonth()+1) < 10 ? "0"+(dat.getMonth()+1) : dat.getMonth()+1;
    var data = dat.getDate()  < 10 ? "0"+(dat.getDate()) : dat.getDate();
    var hour = dat.getHours()  < 10 ? "0"+(dat.getHours()) : dat.getHours();
    var min =  dat.getMinutes()  < 10 ? "0"+(dat.getMinutes()) : dat.getMinutes();
    var seon = dat.getSeconds() < 10 ? "0"+(dat.getSeconds()) : dat.getSeconds();
                 
    var newDate = year +"-"+ mon +"-"+ data +" "+ hour +":"+ min +":"+ seon;
    return newDate;
}
export default {
    props: ['questionnaireId'],
    components: {
        China
    },
    data() {
        return {
            areaCode: '000000000000', // 当前用的areaCode
            areaLevel: 0, // 当前用的areaCode
            areaName: 'china', // 当前用的areaName
            mapNameList: [], // 当前地图上的地区名字
            mapCodeList: [], // 当前地图上的地区Code
            areaStatistic: [],
            detail: [],
            nowDate : format(new Date())
        }
    },

    updated() {
        this.detail.forEach((item, index) => {
            if (item.type == 'text') {
                this.setText(index)
            } else {
                this.changeValue(index, 2)
            }
        })
    },
    mounted() {
        this.dataAnalysis()
    },
    methods: {
        // 获取表格数据
        getTableData() {
            // designOpera({
            //   opera_type: "get_text_answer_detail",
            //   questionId: this.questionId,
            //   currentPage: this.currentPage,
            //   pageSize: this.pageSize
            // }).then(data => {
            //   console.log(data);
            //   this.tableData = data.detail;
            //   this.total = data.total;
            // });
        },
        sizeChange() {
            this.getTableData();
        },
        currentChange() {
            this.getTableData();
        },
        //查看文本回答详情
        lookTextDetail(questionId) {
            this.tableData = [];
            this.pageSize = 10;
            this.total = 0;
            this.currentPage = 1;
            this.dialogTableVisible = true;
            this.questionId = questionId;
            this.getTableData();
        },
        //切换图表
        changeValue(num, value) {
            if (value == 1) {
                this.setImg(num);
            } else if (value == 2) {
                this.setPar(num);
            } else if (value == 3) {
                this.setRing(num);
            } else if (value == 4) {
                this.setTz(num);
            }
        },
        //      请求后端数据
        dataAnalysis() {
            this.loading = true;
            this.detail = [];
            analysis({
                questionnaireId: this.questionnaireId
            }).then(data => {
                this.detail = data.data;
            });
        },

        //柱状图
        setImg(id) {
            let myChart = this.$echarts.init(document.getElementById('chartBar' + id));
            var option = {
                tooltip: {},
                legend: {
                    data: ["数量"]
                },
                dataset: {
                    dimensions: ["option", "count", "percent"],
                    source: this.detail[id].result
                },
                xAxis: {
                    type: "category"
                },
                yAxis: {},
                series: [
                    {
                        name: "数量：",
                        type: "bar",
                        barWidth: 30,
                        color: "deepskyblue"
                    }
                ]
            };
            myChart.setOption(option);
        },
        // 饼状图
        setPar(id) {
            let myChart = this.$echarts.init(document.getElementById('chartBar' + id));
            var option = {
                tooltip: {},

                color: ["#409EFF", "#FFB54D", "#FF7466", "#44DB5E"],
                legend: {
                    data: ["数量"]
                },
                dataset: {
                    dimensions: ["option", "count", "percent"],
                    source: this.detail[id].result
                },
                series: [
                    {
                        name: "统计结果：",
                        type: "pie",
                        radius: "55%",
                        center: ["50%", "50%"],
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: "rgba(0, 0, 0, 0.5)"
                            }
                        }
                    }
                ]
            };
            myChart.setOption(option);
        },
        // 圆环图
        setRing(id) {
            //console.log(id);
            let myChart = this.$echarts.init(document.getElementById('chartBar' + id));
            var option = {
                tooltip: {},
                legend: {},
                color: ["#409EFF", "#FFB54D", "#FF7466", "#44DB5E"],
                dataset: {
                    dimensions: ["option", "count", "percent"],
                    source: this.detail[id].result
                },
                series: [
                    {
                        name: "数量：",
                        type: "pie",
                        radius: ["50%", "70%"],
                        avoidLabelOverlap: false,
                        label: {
                            normal: {
                                show: false,
                                position: "center"
                            },
                            emphasis: {
                                show: true,
                                textStyle: {
                                    fontSize: "30",
                                    fontWeight: "bold"
                                }
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false
                            }
                        }
                    }
                ]
            };
            myChart.setOption(option);
        },
        //圆环图
        setTz(id) {
            let myChart = this.$echarts.init(document.getElementById('chartBar' + id));
            var option = {
                tooltip: {
                    trigger: "axis",
                    axisPointer: {
                        type: "shadow"
                    }
                },
                dataset: {
                    dimensions: ["option", "count", "percent"],
                    source: this.detail[id].result
                },
                grid: {
                    left: "3%",
                    right: "4%",
                    bottom: "3%",
                    containLabel: true
                },
                xAxis: {
                    type: "value",
                    boundaryGap: [0, 0.01]
                },
                yAxis: {
                    type: "category"
                },
                series: [
                    {
                        name: "数量：",
                        type: "bar",
                        barWidth: 30,
                        color: "#409EFF"
                    }
                ]
            };
            myChart.setOption(option);
        },
        //文本内容
        setText(id) {
            let myChart = this.$echarts.init(document.getElementById('chartBar' + id));
            this.detail[id].result = [{ option: 'aa' }, { option: 'bb' }]

            if (this.detail[id].result) {
                const groupByCategory = [];
                const array = this.detail[id].result.reduce((group, res) => {
                    const { option } = res;
                    group[option] = group[option] || [];
                    group[option].push(res);
                    return group;
                }, {})
                Object.keys(array).forEach((key) => {
                    groupByCategory.push({ name: key, value: array[key].length })
                });;

                const option = {
                    series: [
                        {
                            shape: 'circle',//形状 'circle', 'cardioid', 'diamond', 'triangle-forward', 'triangle', 'pentagon', 'star'
                            //maskImage: maskImage  ,//使用base64 词云配置背景图片
                            type: "wordCloud",
                            left: "center",
                            top: "center",
                            width: "100%",
                            height: "100%",
                            right: null,
                            backgroundColor: '#FAF2F2FF',
                            bottom: null,
                            sizeRange: (() => {
                                return [
                                    12, 60
                                ];
                            })(),// 词的大小，最小12px，最大60px，可以在这个范围调整词的大小
                            rotationRange: [-45, 0, 45, 90],
                            rotationStep: (() => {
                                return 15;
                            })(),// // 词间距，数值越小，间距越小，这里间距太小的话，会出现大词把小词套住的情况，比如一个大的口字，中间会有比较大的空隙，这时候他会把一些很小的字放在口字里面，这样的话，鼠标就无法选中里面的那个小字
                            gridSize: 30,
                            drawOutOfBound: false,
                            // 这是全局的文字样式，相对应的还可以对每个词设置字体样式
                            textStyle: {
                                // fontFamily: 'sans-serif',
                                fontFamily: 'Arial',
                                fontWeight: 'normal',
                                // 颜色可以用一个函数来返回字符串(用来控制颜色的范围)
                                color: function () {
                                    // Random color
                                    return 'rgb(' + [
                                        Math.round(Math.random() * 160),
                                        Math.round(Math.random() * 160),
                                        Math.round(Math.random() * 160)
                                    ].join(',') + ')';
                                },
                                normal: {
                                    fontFamily: "sans-serif",
                                    fontWeight: "normal",
                                    color: function () {
                                        return (
                                            "rgb(" +
                                            [
                                                Math.round(Math.random() * 160),
                                                Math.round(Math.random() * 160),
                                                Math.round(Math.random() * 160)
                                            ].join(",") +
                                            ")"
                                        );
                                    }
                                },
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowColor: "#111111"
                                }
                            },
                            //动画
                            layoutAnimation: true,
                            data: groupByCategory
                        },
                    ]
                };
                myChart.setOption(option)
            }
        }
    }
}
</script>
<style>
.Commit {
    width: 100%;
}

.Commit .question {
    max-width: 1500px;
    display: inline-block;
    margin: 5px;
}


.Commit .img {
    width: 300px;
    height: 250px;
}

.Commit .bing {
    width: 300px;
    height: 250px;
}

.Commit .ring {
    width: 300px;
    height: 250px;
}

.Commit .cloud {
    width: 300px;
    height: 250px;
}

.Commit .tz {
    width: 300px;
    height: 250px;
}


.el-statistic {
    width: 100%;
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    color: #000;
    font-size: 14px;
    font-variant: tabular-nums;
    line-height: 1.5715;
    list-style: none;
    font-feature-settings: "tnum";
    text-align: center;
}

.el-statistic .head {
    margin-bottom: 4px;
    color: rgba(0, 0, 0, .45);
    font-size: 14px;
}

.el-statistic .con {
    display: flex;
    justify-content: center;
    align-items: center;
}

.el-statistic .con span {
    display: inline-block;
    margin: 0;
    line-height: 100%;
}

.el-statistic .con .number {
    font-size: 20px;
    padding: 0 4px;
}
</style>