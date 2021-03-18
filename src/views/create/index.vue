<template>
    <div class="create-container">
        <h1 class="title">快来创建属于你的知识图谱！</h1>
        <div class="createPane">
        <el-tabs v-model="createType"
                 type="border-card"
                 style="width:550px;display: inline-block"
                 @tab-click="handleClick">
            <el-tab-pane label="上传csv文件" name="importCSV">
                csv文件格式：节点-节点-关系 三元组
                <el-upload
                        drag
                        action="http://localhost:8080/coinservice/file/getCsv"
                        class="uploading"
                        on-success="handleCsvSuccess"
                        data="uploadParam"
                        accept=".csv"
                        auto-upload="false"
                        ref="upload"
                >
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                </el-upload>
            </el-tab-pane>
            <el-tab-pane label="上传json文件" name="importJSON">
                json文件格式：
                <el-upload
                        drag
                        class="uploading"
                        on-success="jsonsuccess"
                        accept=".json"
                        auto-upload="false"
                >
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                </el-upload>
            </el-tab-pane>
            <el-tab-pane label="上传xml文件" name="importXML">
                xml文件格式：
                <el-upload
                        drag
                        :action="uploadUrl"
                        class="uploading"
                        on-success="xmlsuccess"
                        accept=".xml"
                        auto-upload="false"
                >
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                </el-upload>
            </el-tab-pane>
            <el-tab-pane label="文本提取生成" name="ExtractFromText">
                <el-input
                        type="textarea"
                        :rows="10"
                        placeholder="请输入要提取知识图谱的内容"
                        v-model="extractedText"
                >

                </el-input>
            </el-tab-pane>
        </el-tabs>
        </div>
        <el-button style="margin-top: 10px;" @click="submitUpload">
            生成图谱
        </el-button>
        <el-button @click="addRouterToEditor">
            进入工作区
        </el-button>
        <el-button @click="testt">测试</el-button>
    </div>
</template>

<script>
    import {testAPI} from "../../api/entity";
    import {mapActions, mapGetters} from "vuex";

    export default {
        name: "index.vue",
        data() {
            return {
                createType: 'importCSV',
                extractedText: '',
                uploadParam: {},
            }
        },
        computed: {
            ...mapGetters([
                'nodeList',
                'testdata',
            ])
        },
        methods: {
            ...mapActions([
                'test'
            ]),
            handleCsvSuccess() {
                this.$refs.upload.clearFiles();
                this.$message({
                    message: '导入中',
                    type: 'success'
                })
            },
            submitUpload() {
                console.log('submiting');
                this.$refs.upload.submit();
                this.$router.push('/editor');
            },
            addRouterToEditor() {
                this.$router.push('/editor');
            },
            testt() {
                console.log('this is test');
                testAPI().then(res => {
                    console.log(res);
                    console.log(res.data);
                    console.log(res.data.data);
                    console.log(res.data.code);
                })
                // testAPI().then(res => {
                //     this.extractedText = res.data;
                //     console.log(this.extractedText);
                // })
                // this.test();
                // console.log(this.testdata);
            }
        }
    }
</script>

<style>
    body{
        margin: 0;
        padding: 0;
        overflow: hidden;
    }
.title{
    padding-top: 8%;
    color: #333361;
    font-size: 50px;
}
.create-container {
    text-align: center;
    margin: auto;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    height: auto;
    width: 100%;
    position: fixed;
    background-size: 100% 100%;
    background-image: url("../../assets/kg-background4.jpg");
}
.uploading {
    margin-top: 10px;
    width: 100%;
}

</style>
