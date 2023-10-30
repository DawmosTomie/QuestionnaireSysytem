<template>
  <div>
    <div v-for="(option, index) in numberOfOptions">
      <v-row>
        <v-col cols="8" xl="8" lg="8" md="8" sm="8">
          <v-text-field :color="themeColor" :prepend-icon="getPrependIcon"
            :append-outer-icon="numberOfOptions >= 2 && isFocused ? 'mdi-window-close' : undefined"
            :disabled="!isFocused" @click:append-outer="deleteOption(index)" v-model="question.options[index].title">
          </v-text-field>
        </v-col>
        <v-col  cols="4" xl="4" lg="4" md="4" sm="4">
          <v-menu offset-y v-if="otherQuesions.length > 0">
            <template v-slot:activator="{ on }">
              <v-btn depressed v-on="on" v-show="isFocused">
                跳转至第{{ question.options[index].jumpTo }}题
              </v-btn>
            </template>
            <v-list>
              <v-list-item v-for="(format, idx) in otherQuesions" :key="idx"  @click="setJumpTo(index,format.sortNo)">
                <v-list-item-title>跳转至第{{ format.sortNo }}题</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-col>
      </v-row>
    </div>
    <v-row v-show="isFocused">
      <v-col cols="10">
        <v-text-field :color="themeColor" :prepend-icon="getPrependIcon" placeholder='添加其他选项' @click="addOption">
        </v-text-field>
      </v-col>
    </v-row>
  </div>
</template>

<script>
export default {
  props: [
    "question",
    "questionIndex",
    "themeColor",
    "focusedFormIndex",
    'otherQuesions'
  ],
  data() {
    return {
      numberOfOptions: this.question.options.length,
    }
  },
  computed: {
    getPrependIcon() {
      switch (this.question.type) {
        case 'radio':
          return 'mdi-circle-outline'
        case 'checkbox':
          return 'mdi-checkbox-blank-outline'
        case 'select':
          return 'mdi-arrow-down-drop-circle'
      }
    },
    isFocused() {
      if (this.focusedFormIndex == this.questionIndex) {
        return true
      } else {
        return false
      }
    }
  },
  methods: {
    setJumpTo(index,sortNo){
      this.question.options[index].jumpTo = sortNo
    },
    addOption() {
      this.numberOfOptions += 1
      this.$emit('addOption')
    },
    deleteOption(option_index) {
      this.numberOfOptions -= 1
      this.$emit('deleteOption', option_index)
    },
  }
}
</script>

<style scoped>

</style>
