<template>
  <v-card class="form-editor" width="36" :style="{ top: distance + 'px' }">
    <v-row>
      <v-col>
        <v-tooltip right>
          <template v-slot:activator="{ on }">
            <v-btn class="form-editor-button" icon @click="addQuestion" v-on="on">
              <v-icon>mdi-plus-circle</v-icon>
            </v-btn>
          </template>
          <span class="tooltip">添加问题</span>
        </v-tooltip>
        <v-tooltip right>
          <template v-slot:activator="{ on }">
            <!-- <v-btn 
              class="form-editor-button"
              icon
              v-on="on">
              <v-icon>mdi-import</v-icon>
            </v-btn> -->
            <DialogBank :themeColor="themeColor" @importQns="importQns"/>
          </template>
          <span class="tooltip">导入问题</span>
        </v-tooltip>
        <!-- <v-tooltip right>
          <template v-slot:activator="{ on }">
            <v-btn 
              class="form-editor-button"
              icon
              v-on="on">
              <v-icon>mdi-format-title</v-icon>
            </v-btn>
          </template>
          <span class="tooltip">Add title and description</span>
        </v-tooltip>
        <v-tooltip right>
          <template v-slot:activator="{ on }">
            <v-btn 
              class="form-editor-button"
              icon
              v-on="on">
              <v-icon>mdi-image</v-icon>
            </v-btn>
          </template>
          <span class="tooltip">Add image</span>
        </v-tooltip>
        <v-tooltip right>
          <template v-slot:activator="{ on }">
            <v-btn 
              class="form-editor-button"
              icon
              v-on="on">
              <v-icon>mdi-youtube</v-icon>
            </v-btn>
          </template>
          <span class="tooltip">Add video</span>
        </v-tooltip>
        <v-tooltip right>
          <template v-slot:activator="{ on }">
            <v-btn 
              class="form-editor-button"
              icon
              v-on="on">
              <v-icon>mdi-note-plus</v-icon>
            </v-btn>
          </template>
          <span class="tooltip">Add section</span>
        </v-tooltip> -->
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import DialogBank from '@/components/widgets/dialogs/DialogQnBank'
export default {
  props: ['themeColor'],
  components: { DialogBank },
  data() {
    return {
      distance: 0,
    }
  },
  methods: {
    addQuestion() {
      this.$emit('addQuestion')
    },
    importQns(val) {
      this.$emit('importQns',val)
    },
    handleScroll() {
      this.distance = window.scrollY
    },
  },
  created() {
    window.addEventListener('scroll', this.handleScroll)
  },
  destroyed() {
    window.removeEventListener('scroll', this.handleScroll)
  },
}
</script>

<style scoped>
/* 50px right to the parent element (v-card) */
.form-editor {
  position: absolute;
  right: 10px;
  z-index: 10;
}

.form-editor-button {
  margin-bottom: 7px;
}
</style>
