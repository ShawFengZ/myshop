var App = function () {

    var _masterCheckbox;
    var _checkbox;

    //用于存放id的数组
    var _idArray;

    //默认的dropzone参数
    var defaultDropzoneOpts = {
        url: "",
        paramName: "dropzFile", // 传到后台的参数名称,
        maxFiles: 1,// 一次性上传的文件数量上限
        maxFilesize: 2, // 文件大小，单位：MB
        acceptedFiles: ".jpg,.gif,.png,.jpeg", // 上传的类型
        addRemoveLinks: true,
        parallelUploads: 1,// 一次上传的文件数量
        dictDefaultMessage: '拖动文件至此或者点击上传',
        dictMaxFilesExceeded: "您最多只能上传"+ this.maxFiles+ "个文件！",
        dictResponseError: '文件上传失败!',
        dictInvalidFileType: "文件类型只能是*.jpg,*.gif,*.png,*.jpeg。",
        dictFallbackMessage: "浏览器不受支持",
        dictFileTooBig: "文件过大上传文件最大支持.",
        dictRemoveLinks: "删除",
        dictCancelUpload: "取消"
    };

    /**
     * 初始化ICheck，私有方法
     * */
    var handlerInitCheckBox = function () {
        //激活
        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass   : 'iradio_minimal-blue'
        });

        //获取控制的checkbox
        _masterCheckbox = $('input[type="checkbox"].minimal.icheck_master');

        //获取全部的checkbox集合
        _checkbox = $('input[type="checkbox"].minimal');
    };

    //全选功能
    var handlerCheckBoxAll = function () {
        _masterCheckbox.on('ifClicked', function (e) {
            if (e.target.checked) {//未选中
                _checkbox.iCheck("uncheck");
            } else { //选中
                _checkbox.iCheck("check");
            }
        });
    };

    /**
     * 批量删除
     * */
    var handlerDeleteMulti = function (url) {
        _idArray = new Array();
        _checkbox.each(function () {
            var _id = $(this).attr("id");
            if (_id!=null && _id!="undefine" &&$(this).is(":checked")) {
                _idArray.push(_id);
            }
        });
        //判断用户是否选择数据项
        if (_idArray.length === 0) {
            $("#modal-message").html("您还没有选择任何数据，请选择至少一条数据！");
        } else {
            $("#modal-message").html("您确定删除选择的数据项吗？");
        }
        //点击删除时弹出模态框
        $("#modal-default").modal('show');

        //如果用户选择了数据项，则调用删除方法
        $("#btnModalOk").bind("click", function () {
            del(_idArray, url);
        });

        /**
         * 当前私有函数的私有函数，删除数据
         * */
        function del(idArray, url) {
            $("#modal-default").modal("hide");
            //如果没有选择则关闭模态框
            if (idArray.length == 0 ) {

            } else {
                setTimeout(function () {
                    $.ajax({
                        "url":url,
                        "data": {"ids":idArray.toString()},
                        //设置为同步执行
                        "type": "POST",
                        "dataType": "JSON",
                        "success": function (data) {
                            //请求成功后，都需要弹出模态框进行提示，所以这里先解绑click事件
                            $("#btnModalOk").unbind("click");
                            if (data.status === 200) {//请求成功，刷新页面
                                $("#btnModalOk").bind("click", function () {
                                    window.location.reload();
                                });

                            } else {//删除失败，确定按钮的事件改为隐藏模态框
                                $("#btnModalOk").bind("click", function () {
                                    $("#modal-default").modal("hide");
                                });
                            }
                            //这里的模态框是必须调用的
                            $("#modal-message").html(data.message);
                            $("#modal-default").modal("show");
                        }
                    });
                },500);

            }
        }

    };

    var handlerInitDataTables = function (url, columns) {
        var _dataTable = $('#dataTable').DataTable({
            "paging": true,
            "info": true,
            "lengthChange": false,
            "ordering": false,
            "processing": true,
            "searching": false,
            "serverSide": true,
            "deferRender": true,
            "ajax":{
                "url": url
            },
            "columns": columns,
            "language": {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            "drawCallback": function (settings, json) {
                handlerInitCheckBox();
                handlerCheckBoxAll();
            }
        });

        return _dataTable;
    };

    /**
     * 查看详情
     * */
    var handlerShowDetail = function (url) {
        //这里是通过Ajax请求html的方式将jsp装载进模态框中
        $.ajax({
            url: url,
            type:"GET",
            dataType: "html",
            success: function (data) {
                $("#modal-detail-body").html(data);
                $("#modal-detail").modal("show");
            }
        });
    };

    var handlerInitZTree = function(url, autoParam, callback) {
        var setting = {
            view:{
                selectedMulti:false
            },
            async: {
                enable: true,
                url:url,
                autoParam:autoParam,
            }
        };

        $.fn.zTree.init($("#myTree"), setting);

        $("#btnModalOk").bind("click", function () {
            var myTree = $.fn.zTree.getZTreeObj("myTree");
            var nodes = myTree.getSelectedNodes();

            //未选择
            if(nodes.length == 0) {
                alert("请选择一个节点");
            }
            //已选择
            else {
                callback(nodes);
            }
        });
    };

    /**
     * 初始化dropzone
     * */
    var handlerInitDropzone = function (opts) {
        //关闭Dropzone的自动发现功能
        Dropzone.autoDiscover = false;
        //defaultDropzoneOpts继承opts
        $.extend(defaultDropzoneOpts, opts);
        console.log(defaultDropzoneOpts.url);
        var myDropzone = new Dropzone(defaultDropzoneOpts.id, defaultDropzoneOpts);
    };

    /**
     * 单独删除
     * */
    var handlerDeleteSingle = function (url, id, msg) {
        // 可选参数
        if (!msg) msg = null;

        // 将 ID 放入数组中，以便和批量删除通用
        _idArray = new Array();
        _idArray.push(id);

        $("#modal-message").html(msg == null ? "您确定删除数据项吗？" : msg);
        $("#modal-default").modal("show");
        // 绑定删除事件
        $("#btnModalOk").bind("click", function () {
            handlerDeleteData(url);
        });
    };


    return {
        /**
         * 初始化
         * */
        init: function () {
            handlerInitCheckBox();
            handlerCheckBoxAll();
        },

        /**
         * 批量删除
         * */
        deleteMulti: function (url) {
            handlerDeleteMulti(url);
        },

        /**
         * 初始化DataTables
         * */
        initDataTables: function (url, columns) {
            return handlerInitDataTables(url, columns);
        },

        /**
         * 显示详情
         * */
        showDetail: function (url) {
            handlerShowDetail(url);
        },

        /**
         * 初始化ZTree
         * */
        initZTree: function (url, autoParam, callback) {
            handlerInitZTree(url, autoParam, callback);
        },

        /**
         * 初始化上传图片组件
         * */
        initDropzone: function (opts) {
            handlerInitDropzone(opts);
        },

        /**
         * 单个删除
         * */
        deleteSingle: function(url, id, msg) {
            handlerDeleteSingle(url, id, msg);
        }
    }
}();

$(document).ready(function () {
    App.init();
});