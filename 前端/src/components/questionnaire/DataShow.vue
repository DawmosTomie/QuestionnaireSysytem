<!--
程序名：数据分析页面
功能：对问卷调查结果的数据进行分析并用图表可视化展示
-->
<template>
  <div id="pdfDom" class="Count" v-loading="loading" element-loading-text="生成中...">
    <!-- <div v-if="!(detail.length == 0)" class="opera-buttons">
      <el-button
        type="primary"
        size="mini"
        @click.native="analysisExportExcel"
        :loading="exportExcelLoading"
        >导出excel</el-button
      >
      <el-button type="success" size="mini" @click.native="exportPdf"
        >导出PDF</el-button
      >
    </div> -->
    <div v-if="this.constData.length == 0">暂时没有数据</div>
    <el-card class="question" v-for="(item, index) in this.data" :key="item.id">
      <div slot="header" class="clearfix">
        <span>{{ index + 1 + "." + item.title }}</span>
      </div>
      <!--如果数据库中的问题类型为单项选择或者多项选择-->
      <!--则将数据库中的数据以表格、柱状图、饼状图、圆环图、条形图的方式进行展示-->
      <div v-if="item.type == 'radio' || item.type == 'checkbox'">
        <el-table size="small" :data="item.result" style="width: 100%" stripe class="table">
          <el-table-column prop="option" label="选项"></el-table-column>
          <el-table-column prop="count" label="数量" width="180"></el-table-column>
          <el-table-column prop="percent" label="占比" width="180"></el-table-column>
        </el-table>
        <br />

        <el-row :gutter="10" justify="start">
          <el-col :span="4">
            <el-button size="mini" type="primary" @click.native="changeValue(index, 1)">柱状图</el-button>
          </el-col>
          <el-col :span="4">
            <el-button size="mini" type="primary" @click.native="changeValue(index, 2)">饼状图</el-button>
          </el-col>
          <el-col :span="4">
            <el-button size="mini" type="primary" @click.native="changeValue(index, 3)">圆环图</el-button>
          </el-col>
          <el-col :span="4">
            <el-button size="mini" type="primary" @click.native="changeValue(index, 4)">条形图</el-button>
          </el-col>
          <el-col :span="4">
            <el-button size="mini" type="primary" @click.native="changeValue(index, 0)">隐藏图表</el-button>
          </el-col>
        </el-row>

        <div :id="'img' + index" class="img" v-show="visible[index] == 1"></div>
        <div :id="'bing' + index" class="bing" v-show="visible[index] == 2"></div>
        <div :id="'ring' + index" class="ring" v-show="visible[index] == 3"></div>
        <div :id="'tz' + index" class="tz" v-show="visible[index] == 4"></div>
      </div>
      <!--如果数据库中的问题类型为text类型则将数据以弹窗表格的形式进行显示-->
      <div v-if="item.type == 'text'">
        <el-button size="mini" type="primary" @click.native="lookTextDetail(item.questionId)">详细内容</el-button>
        <el-button size="mini" type="primary" @click.native="setText(index)">展开词云图</el-button>
        <div :id="'cloud' + index" class="cloud"></div>
        <!-- <el-button
          size="mini"
          type="primary"
          plain
          @click.native="answerText2Excel(item.questionId)"
          :loading="item.questionId == answerText2ExcelQeustionId"
          >导出excel</el-button
        > -->
      </div>
    </el-card>
    <el-dialog title="详细内容" :visible.sync="dialogTableVisible">
      <el-table :data="tableData">
        <el-table-column property="context" label="答案"></el-table-column>
      </el-table>
      <el-pagination @size-change="sizeChange" @current-change="currentChange" :current-page.sync="currentPage"
        :page-sizes="[10, 20, 50, 100]" :page-size.sync="pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-dialog>
  </div>
</template>

<script>
  import {
    designOpera,
    analysis,
    answerView
  } from "../../utils/api";

  export default {
    data() {
      return {
        questionCommitList: [],
        dialogTableVisible: false,
        visible: [],
        loading: false,
        detail: [],
        answerList: [],
        //questions: [],
        currentPage: 1,
        pageSize: 10,
        total: 0,
        tableData: [],
        questionId: 0,
        wjId: 0,
        exportExcelLoading: false,
        answerText2ExcelQeustionId: 0,
        commitId: 0,
        data: [],
        constDetail: [],
        constData: [],
      };
    },
    mounted() {
      this.wjId = this.$route.params.questionnaireId
      this.dataAnalysis(this.wjId)
      console.log(this.visible);
    },
    methods: {
      //answerText2Excel(questionId) {
      //this.answerText2ExcelQeustionId = questionId;
      // designOpera({
      //   opera_type: "answer_text_to_excel",
      //   questionId: questionId
      // }).then(data => {
      //   this.doDownload(data.b64data, data.filename, "excel");
      //   this.answerText2ExcelQeustionId = 0;
      // });
      //},
      // // 导出pdf
      // exportPdf() {
      //   this.$alert("正在开发...", "提示");
      // },
      // // 导出excel
      // analysisExportExcel() {
      //   this.exportExcelLoading = true;
      //   designOpera({
      //     opera_type: "analysis_export_excel",
      //     wjId: this.wjId
      //   }).then(data => {
      //     this.doDownload(data.b64data, data.filename, "excel");
      //     this.exportExcelLoading = false;
      //   });
      // },
      // // doDownload(data, filename, type) {
      //   var b64data = data; //base64数据
      //   // b64data = b64data.replace("data:" + type + ";base64,", "");
      //   var bdata = this.dataURLtoBlob(b64data);
      //   if (!b64data) {
      //     return;
      //   }
      //   let url = window.URL.createObjectURL(new Blob([bdata]));
      //   let link = document.createElement("a");
      //   link.style.display = "none";
      //   link.href = url;
      //   //        link.download = 'ea7c0cf24153e0cd62bc8b64841fd84d.jpg'; //下载后文件名
      //   link.setAttribute("download", filename);

      //   document.body.appendChild(link);
      //   link.click();
      // },
      // dataURLtoBlob(dataurl) {
      //   //          dataurl=dataurl.replace('data:application/json;base64,','')
      //   console.log(dataurl);
      //   var bstr = atob(dataurl),
      //     n = bstr.length,
      //     u8arr = new Uint8Array(n);
      //   while (n--) {
      //     u8arr[n] = bstr.charCodeAt(n);
      //   }
      //   return u8arr;
      // },
      // 获取表格数据
      getTableData(questionId) {
        let result = this.getQuestionText(questionId);
        //this.tableData=result.detail;
        // 将原始数据转换为具有 context 属性的新对象数组
        const tableData = result.detail.map(item => {
          return {
            context: item
          };
        });
        // 将转换后的 tableData 赋值给 this.tableData
        this.tableData = tableData;
        console.log(this.tableData)
        this.total = result.total;


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
        this.getTableData(questionId);
      },
      //切换图表
      changeValue(num, value) {
        this.$set(this.visible, num, value);
        console.log("num=" + num);
        console.log("value=" + value);
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
      dataAnalysis(wjId) {
        if (wjId) {
          this.data = [];
          this.detail = [];
          this.loading = true;
          this.wjId = wjId;
          //console.log(this.wjId);
          (async () => {
            answerView({
              questionnaireId: this.wjId
            }).then(data => {
              console.log(data);
              let questions = data.data.questions;
              this.visible = [];
              this.loading = false;
              //console.log(this.detail.length);
              // for (let i = 0; i < questions.length; i++) {
              //   // 在循环中执行的操作
              //   this.detail[i].title = this.questions[i].questionTitle;
              //   this.detail[i].type = this.question[i].type;
              //   this.detail[i].questionId = this.question[i].questionId;
              // }
              this.$post('/api/qnCommit/get', {
                  questionnaireId: wjId,
                })
                .then(data => {
                  this.questionCommitList = data;
                  //console.log(this.questionCommitList);
                  let flag = 0;

                  (async () => {
                    for (const commit of this.questionCommitList) {
                      let answerList = [];
                      const data = await this.$post('/api/qnAnswer/get', {
                        commitId: commit.commitId,
                      });
                      this.answerList = data;
                      // console.log(this.answerList);
                      for (const item of this.answerList) {
                        let detailItem = [];
                        for (let i = 0; i < questions.length; i++) {
                          const q = questions[i];
                          if (item.questionId === q.questionId) {
                            detailItem.title = q.questionTitle;
                            detailItem.type = q.type;
                            detailItem.questionId = q.questionId;
                            detailItem.result = item.anwser;
                            // console.log(item.anwser);
                            // console.log(detailItem.result);
                            this.detail.push(detailItem);
                          }
                        }
                      }
                    }

                    flag = 1;
                    if (flag == 1) {
                      if (this.constDetail.length == 0) this.constDetail = this.detail;
                      let flag2 = 0;
                      this.data = [];
                      const uniqueQuestionIds = [];
                      for (const item of this.detail) {
                        if (!uniqueQuestionIds.includes(item.questionId)) {
                          uniqueQuestionIds.push(item.questionId);
                          item.result = this.getQuestionAnalysis(item.questionId);
                          this.data.push(item);
                        }
                      }
                      flag2 = 1;
                      if (flag2 == 1) {
                        if (this.constData.length == 0) this.constData = this.data;
                      }
                    }

                  })();


                  //   for (const commit of this.questionCommitList) {
                  //     let answerList = [];
                  //     this.$post('/api/qnAnswer/get', {
                  //         commitId: commit.commitId,
                  //       })
                  //       .then(data => {
                  //         this.answerList = data;
                  //         //console.log(this.answerList);
                  //         for (const item of this.answerList) {
                  //           let detailItem = [];
                  //           for (let i = 0; i < this.questions.length; i++) {
                  //             const q = this.questions[i];
                  //             if (item.questionId === q.questionId) {
                  //               // 在这里执行与相等情况下的操作
                  //               detailItem.title = q.questionTitle;
                  //               detailItem.type = q.type;
                  //               detailItem.questionId = q.questionId;
                  //               detailItem.result = item.anwser;
                  //               //console.log(item.anwser);
                  //               //console.log(detailItem.result);
                  //               this.detail.push(detailItem);
                  //             }
                  //           }
                  //         }
                  //       });
                  //   };
                  //   flag = 1;
                  //   if (flag == 1) {
                  //     if(this.constDetail.length==0) this.constDetail=this.detail;
                  //     let flag2 = 0;
                  //     this.data = [];
                  //     //detailItem.result = this.getQuestionAnalysis(detailItem.questionId);
                  //     //console.log(this.detail);
                  //     const uniqueQuestionIds = [];
                  //     for (const item of this.detail) {
                  //       if (!uniqueQuestionIds.includes(item.questionId)) {
                  //         uniqueQuestionIds.push(item.questionId);
                  //         item.result = this.getQuestionAnalysis(item.questionId);
                  //         this.data.push(item);
                  //       };
                  //     };
                  //     flag2=1;
                  //     if(flag2==1) {
                  //       if(this.constData.length==0) this.constData=this.data;
                  //     }
                  // }
                });
            });
            console.log(this.data);
            this.dialogTableVisible = false;
          })();
        }
      },


      getQuestionAnalysis(questionId) {
        const result = [];
        const options = {};
        // 遍历 detail 数组
        for (let i = 0; i < this.constDetail.length; i++) {
          const element = this.constDetail[i];

          // 判断 questionId 是否相等
          if (element.questionId === questionId && !isNaN(element.result)) {
            const answer = element.result;

            // 统计每个选项的答案数量
            if (options[answer]) {
              options[answer] += 1;
            } else {
              options[answer] = 1;
            }
          }
        }

        // 计算每个选项的占比
        const totalAnswers = Object.values(options).reduce((a, b) => a + b, 0);
        for (const option in options) {
          const count = options[option];
          const percent = (count / totalAnswers) * 100;

          result.push({
            option: option,
            count: count,
            percent: percent
          });
        }

        return result;
      },

      getQuestionText(questionId) {
        let result = {
          detail: [],
          total: 0,
        };
        for (let i = 0; i < this.constDetail.length; i++) {
          const element = this.constDetail[i];
          // 判断 questionId 是否相等
          if (element.questionId === questionId) {
            result.detail.push(element.result);
            result.total++;
          }

        }
        result.detail.shift();
        result.total--;
        console.log(result);
        return result
      },



      //柱状图
      setImg(id) {
        console.log(id);
        console.log(this.detail[id].result);
        let myChart = this.$echarts.init(document.getElementById("img" + id));
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
          series: [{
            name: "数量：",
            type: "bar",
            barWidth: 30,
            color: "deepskyblue"
          }]
        };
        myChart.setOption(option);
      },
      // 饼状图
      setPar(id) {
        let myChart = this.$echarts.init(document.getElementById("bing" + id));
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
          series: [{
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
          }]
        };
        myChart.setOption(option);
      },
      // 圆环图
      setRing(id) {
        //console.log(id);
        let myChart = this.$echarts.init(document.getElementById("ring" + id));
        var option = {
          tooltip: {},
          legend: {},
          color: ["#409EFF", "#FFB54D", "#FF7466", "#44DB5E"],
          dataset: {
            dimensions: ["option", "count", "percent"],
            source: this.detail[id].result
          },
          series: [{
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
          }]
        };
        myChart.setOption(option);
      },
      //圆环图
      setTz(id) {
        let myChart = this.$echarts.init(document.getElementById("tz" + id));
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
          series: [{
            name: "数量：",
            type: "bar",
            barWidth: 30,
            color: "#409EFF"
          }]
        };
        myChart.setOption(option);
      },
      //文本内容
      setText(id) {
        let myChart = this.$echarts.init(document.getElementById("cloud" + id));
        this.detail[id].result = [{
            option: 'a'
          }, {
            option: 'b'
          }, {
            option: 'c'
          }, {
            option: 'd'
          },
          {
            option: 'e'
          }, {
            option: 'f'
          }, {
            option: 'g'
          }, {
            option: 'h'
          },
          {
            option: 'i'
          }, {
            option: 'j'
          }, {
            option: 'k'
          }, {
            option: 'l'
          },
          {
            option: 'm'
          }, {
            option: 'n'
          }, {
            option: 'o'
          }, {
            option: 'p'
          },
          {
            option: 'q'
          }, {
            option: 'r'
          }, {
            option: 's'
          }, {
            option: 't'
          },
          {
            option: 'u'
          }, {
            option: 'v'
          }, {
            option: 'w'
          }, {
            option: 'x'
          },
          {
            option: 'y'
          }, {
            option: 'z'
          }, {
            option: 'apple'
          }, {
            option: 'banana'
          },
          {
            option: 'cherry'
          }, {
            option: 'date'
          }, {
            option: 'eggplant'
          },
          {
            option: 'fig'
          }, {
            option: 'grape'
          }, {
            option: 'honeydew'
          },
          {
            option: 'kiwi'
          }, {
            option: 'lemon'
          }, {
            option: 'mango'
          },
          {
            option: 'nectarine'
          }, {
            option: 'orange'
          }, {
            option: 'pear'
          },
          {
            option: 'quince'
          }, {
            option: 'raspberry'
          }, {
            option: 'strawberry'
          },
          {
            option: 'tomato'
          }, {
            option: 'watermelon'
          }, {
            option: 'hello'
          },
          {
            option: 'world'
          }, {
            option: 'good'
          }, {
            option: 'morning'
          },
          {
            option: 'afternoon'
          }, {
            option: 'evening'
          }, {
            option: 'night'
          },
          {
            option: 'yes'
          }, {
            option: 'no'
          }, {
            option: 'maybe'
          },
          {
            option: 'thank you'
          }, {
            option: 'please'
          }, {
            option: 'sorry'
          },
          {
            option: 'excuse me'
          }, {
            option: 'how are you'
          }, {
            option: 'goodbye'
          },
          {
            option: 'nice to meet you'
          }, {
            option: 'congratulations'
          },
          {
            option: 'happy'
          }, {
            option: 'sad'
          }, {
            option: 'love'
          },
          {
            option: 'friend'
          }, {
            option: 'family'
          }, {
            option: 'food'
          },
          {
            option: 'travel'
          }, {
            option: 'music'
          }, {
            option: 'movie'
          },
          {
            option: 'book'
          }, {
            option: 'art'
          }, {
            option: 'sport'
          },
          {
            option: 'work'
          }, {
            option: 'study'
          }, {
            option: 'money'
          },
          {
            option: 'success'
          }, {
            option: 'failure'
          }, {
            option: 'peace'
          }, {
            option: 'hope'
          }, {
            option: 'dream'
          },
          {
            option: 'laughter'
          }, {
            option: 'joy'
          }, {
            option: 'happiness'
          },
          {
            option: 'kindness'
          }, {
            option: 'compassion'
          }, {
            option: 'gratitude'
          },
          {
            option: 'success'
          }, {
            option: 'achievement'
          }, {
            option: 'victory'
          },
          {
            option: 'challenge'
          }, {
            option: 'opportunity'
          }, {
            option: 'growth'
          },
          {
            option: 'inspiration'
          }, {
            option: 'motivation'
          }, {
            option: 'creativity'
          },
          {
            option: 'wisdom'
          }, {
            option: 'knowledge'
          }, {
            option: 'learning'
          },
          {
            option: 'patience'
          }, {
            option: 'perseverance'
          }, {
            option: 'determination'
          },
          {
            option: 'faith'
          }, {
            option: 'belief'
          }, {
            option: 'trust'
          },
          {
            option: 'freedom'
          }, {
            option: 'justice'
          }, {
            option: 'equality'
          },
          {
            option: 'community'
          }, {
            option: 'together'
          }, {
            option: 'harmony'
          },
          {
            option: 'nature'
          }, {
            option: 'environment'
          }, {
            option: 'sustainability'
          },
          {
            option: 'technology'
          }, {
            option: 'innovation'
          }, {
            option: 'future'
          },
          {
            option: 'health'
          }, {
            option: 'wellness'
          }, {
            option: 'balance'
          },
          {
            option: 'love'
          }, {
            option: 'friendship'
          }, {
            option: 'family'
          },
          {
            option: 'celebration'
          }, {
            option: 'memories'
          }, {
            option: 'adventure'
          },
          {
            option: 'journey'
          }, {
            option: 'discovery'
          }, {
            option: 'wonder'
          }, {
            option: '和平'
          }, {
            option: '希望'
          }, {
            option: '梦想'
          },
          {
            option: '笑声'
          }, {
            option: '快乐'
          }, {
            option: '幸福'
          },
          {
            option: '善良'
          }, {
            option: '同情'
          }, {
            option: '感激'
          },
          {
            option: '成功'
          }, {
            option: '成就'
          }, {
            option: '胜利'
          },
          {
            option: '挑战'
          }, {
            option: '机遇'
          }, {
            option: '成长'
          },
          {
            option: '灵感'
          }, {
            option: '动力'
          }, {
            option: '创造力'
          },
          {
            option: '智慧'
          }, {
            option: '知识'
          }, {
            option: '学习'
          },
          {
            option: '耐心'
          }, {
            option: '毅力'
          }, {
            option: '决心'
          },
          {
            option: '信仰'
          }, {
            option: '信念'
          }, {
            option: '信任'
          },
          {
            option: '自由'
          }, {
            option: '正义'
          }, {
            option: '平等'
          },
          {
            option: '社区'
          }, {
            option: '团结'
          }, {
            option: '和谐'
          },
          {
            option: '自然'
          }, {
            option: '环境'
          }, {
            option: '可持续发展'
          },
          {
            option: '技术'
          }, {
            option: '创新'
          }, {
            option: '未来'
          },
          {
            option: '健康'
          }, {
            option: '福祉'
          }, {
            option: '平衡'
          },
          {
            option: '爱'
          }, {
            option: '友谊'
          }, {
            option: '家庭'
          },
          {
            option: '庆祝'
          }, {
            option: '回忆'
          }, {
            option: '冒险'
          },
          {
            option: '旅程'
          }, {
            option: '发现'
          }, {
            option: '奇迹'
          }, {
            option: '你好'
          }, {
            option: '谢谢'
          }, {
            option: '对不起'
          },
          {
            option: '请'
          }, {
            option: '不'
          }, {
            option: '是'
          },
          {
            option: '好'
          }, {
            option: '可以'
          }, {
            option: '一起'
          },
          {
            option: '再见'
          }, {
            option: '朋友'
          }, {
            option: '家人'
          },
          {
            option: '工作'
          }, {
            option: '学习'
          }, {
            option: '吃饭'
          },
          {
            option: '喝水'
          }, {
            option: '睡觉'
          }, {
            option: '放松'
          },
          {
            option: '忙'
          }, {
            option: '累'
          }, {
            option: '开心'
          },
          {
            option: '难过'
          }, {
            option: '生日'
          }, {
            option: '节日'
          },
          {
            option: '旅行'
          }, {
            option: '购物'
          }, {
            option: '电影'
          },
          {
            option: '音乐'
          }, {
            option: '运动'
          }, {
            option: '健身'
          },
          {
            option: '假期'
          }, {
            option: '做梦'
          }, {
            option: '幽默'
          },
          {
            option: '笑话'
          }, {
            option: '好玩'
          }, {
            option: '好吃'
          },
          {
            option: '美丽'
          }, {
            option: '温暖'
          }, {
            option: '感动'
          },
          {
            option: '激动'
          }, {
            option: '期待'
          }, {
            option: '惊喜'
          },
          {
            option: '勇敢'
          }, {
            option: '挑战'
          }, {
            option: '成功'
          },
          {
            option: '失败'
          }, {
            option: '希望'
          }, {
            option: '梦想'
          },
          {
            option: '努力'
          }, {
            option: '坚持'
          }, {
            option: '放弃'
          },
          {
            option: '自信'
          }, {
            option: '忍耐'
          }, {
            option: '理解'
          },
          {
            option: '宽容'
          }, {
            option: '尊重'
          }, {
            option: '友善'
          },
          {
            option: '感谢'
          }, {
            option: '祝福'
          }, {
            option: '幸福'
          }
        ]

        if (this.detail[id].result) {
          const groupByCategory = [];
          const array = this.detail[id].result.reduce((group, res) => {
            const {
              option
            } = res;
            group[option] = group[option] || [];
            group[option].push(res);
            return group;
          }, {})
          Object.keys(array).forEach((key) => {
            groupByCategory.push({
              name: key,
              value: array[key].length
            })
          });;

          const option = {
            series: [{
              shape: 'circle', //形状 'circle', 'cardioid', 'diamond', 'triangle-forward', 'triangle', 'pentagon', 'star'
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
              })(), // 词的大小，最小12px，最大60px，可以在这个范围调整词的大小
              rotationRange: [-45, 0, 45, 90],
              rotationStep: (() => {
                  return 15;
                })
                (), // // 词间距，数值越小，间距越小，这里间距太小的话，会出现大词把小词套住的情况，比如一个大的口字，中间会有比较大的空隙，这时候他会把一些很小的字放在口字里面，这样的话，鼠标就无法选中里面的那个小字
              gridSize: 30,
              drawOutOfBound: false,
              // 这是全局的文字样式，相对应的还可以对每个词设置字体样式
              textStyle: {
                // fontFamily: 'sans-serif',
                fontFamily: 'Arial',
                fontWeight: 'normal',
                // 颜色可以用一个函数来返回字符串(用来控制颜色的范围)
                color: function() {
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
                  color: function() {
                    return (
                      "rgb(" + [
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
            }, ]
          };
          myChart.setOption(option)
        }
      }
    }
  };
</script>
<style scoped>
  .Count {}

  .Count .question {
    max-width: 1500px;
    width: 80%;
    display: inline-block;
    margin: 5px;
    text-align: left;
  }

  .Count .table {}

  .Count .img {
    width: 500px;
    height: 300px;
  }

  .Count .bing {
    width: 500px;
    height: 300px;
  }

  .Count .ring {
    width: 500px;
    height: 300px;
  }

  .Count .cloud {
    width: 500px;
    height: 300px;
  }

  .Count .tz {
    width: 500px;
    height: 300px;
  }

  .opera-buttons {
    padding: 10px;
  }
</style>