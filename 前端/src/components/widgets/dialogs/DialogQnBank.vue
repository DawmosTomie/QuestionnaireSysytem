<template>
    <v-dialog max-width="600px" v-model="dialog" :fullscreen="$viewport.width < 450">
        <template v-slot:activator="{ on: dialog }">
            <v-tooltip bottom>
                <template v-slot:activator="{ on: tooltip }">
                    <v-btn icon v-on="{ ...tooltip, ...dialog }">
                        <v-icon>mdi-import</v-icon>
                    </v-btn>
                </template>
                <span class="tooltip">题库导入</span>
            </v-tooltip>
        </template>
        <v-card tile>
            <v-card-title v-bind:style="{ backgroundColor: themeColorList[themeColor] }">
                <v-btn class="close-button" icon color="white" v-show="$viewport.width < 450" @click="dialog = false">
                    <v-icon>mdi-close</v-icon>
                </v-btn>
                <span class="headline font-weight-medium dialog-title">设置</span>
                <div class="flex-grow-1"></div>
                <v-btn icon color="white" v-show="$viewport.width < 450" @click="dialog = false">保存</v-btn>
            </v-card-title>
            <QnBank :isImport="true" ref="qnBank" v-if="dialog"></QnBank>
            <v-divider></v-divider>
            <v-card-actions v-show="$viewport.width > 450">
                <div class="flex-grow-1"></div>
                <v-btn text @click="dialog = false">取消</v-btn>
                <v-btn :color="themeColor" text @click="importQn">确定</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>
  
<script>
import QnBank from '@/views/qn-bank/qn-bank'
export default {
    props: ["themeColor"],
    components: { QnBank },
    data() {
        return {
            dialog: false,
            active: true,
            checkbox1: true,
            checkbox2: true,
            checkbox3: true,
            checkbox4: true,
            isQuiz: false,
            isEmailCollected: false,
            selectedMax: "1GB",
            maximumSizeOfFiles: ['1GB', '10GB', '100GB', '1TB'],
            themeColorList: {
                'red darken-1': '#E53935',
                'deep-purple darken-1': '#5E35B1',
                'indigo darken-1': '#3949AB',
                'blue darken-1': '#1E88E5',
                'light-blue darken-1': '#039BE5',
                'cyan darken-1': '#00ACC1',
                'deep-orange darken-1': '#F4511E',
                'orange darken-1': '#FB8C00',
                'teal darken-1': '#00897B',
                'green darken-1': '#43A047',
                'blue-grey darken-1': '#546E7A',
                'grey darken-1': '#757575',
            },
        }
    },
     methods:{
        importQn(){
            this.dialog = false
            const checkedQns =  this.$refs.qnBank.detail.filter(qn => {return qn.checked}).map(qn => {
                qn.questionId=''
                return qn
            })
            this.$emit('importQns',checkedQns)
        }
     }
}
</script>
  
<style scoped>
.dialog-title {
    color: #FFFFFF;
}

.close-button {
    padding-right: 30px;
}
</style>