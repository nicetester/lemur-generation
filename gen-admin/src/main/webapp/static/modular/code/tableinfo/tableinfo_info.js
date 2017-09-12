/**
 * 初始化表信息管理详情对话框
 */
var TableInfoInfoDlg = {
    TableInfoInfoData : {}
};

/**
 * 清除数据
 */
TableInfoInfoDlg.clearData = function() {
    this.TableInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TableInfoInfoDlg.set = function(key, val) {
    this.TableInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TableInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TableInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.TableInfo.layerIndex);
}

/**
 * 收集数据
 */
TableInfoInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
TableInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/TableInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.TableInfo.table.refresh();
        TableInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.TableInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TableInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/TableInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.TableInfo.table.refresh();
        TableInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.TableInfoInfoData);
    ajax.start();
}

$(function() {

});
