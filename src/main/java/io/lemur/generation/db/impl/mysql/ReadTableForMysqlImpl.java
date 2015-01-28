package io.lemur.generation.db.impl.mysql;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.lemur.generation.entity.field.DataBaseFieldEntity;
import io.lemur.generation.entity.table.DataBaseTableEntity;
import io.lemur.generation.exception.GenerationRunTimeException;
import io.lemur.generation.db.BaseReadTable;
import io.lemur.generation.db.IReadTable;
import io.lemur.generation.util.NameUtil;

/**
 * MySql数据库的实现类
 * 
 * @author JueYue
 * @date 2014年12月21日
 */
public class ReadTableForMysqlImpl extends BaseReadTable implements IReadTable {

    private static String       TABLE_SQL  = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_NAME = '%s' AND TABLE_SCHEMA = '%s'";

    private static String       FIELDS_SQL = "SELECT column_name as fieldName, data_type as fieldType, column_comment as _comment, numeric_precision as _precision, numeric_scale as scale, character_maximum_length as charmaxLength,is_nullable as nullable from information_schema.columns where table_name = '%s' and table_schema = '%s'";

    private static final Logger LOGGER     = LoggerFactory.getLogger(ReadTableForMysqlImpl.class);

    @Override
    public DataBaseTableEntity read(String tableName) {
        try {
            DataBaseTableEntity entity = getTableEntiy(tableName, TABLE_SQL);
            entity.setName(NameUtil.getEntityHumpName(entity.getTableName()));
            entity.setFields(getTableFields(tableName, FIELDS_SQL));
            hanlderFields(entity.getFields());
            return entity;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e.fillInStackTrace());
            throw new GenerationRunTimeException("获取表格数据发生异常");
        }
    }

    /**
     * 处理一遍字段,根据字段类型做合适的处理
     * 
     * @param fields
     */
    private void hanlderFields(List<DataBaseFieldEntity> fields) {
        DataBaseFieldEntity entity;
        for (int i = 0, le = fields.size(); i < le; i++) {
            entity = fields.get(i);
            entity.setChinaName(getFieldName(entity.getFieldName(), entity.getComment()));
            if (entity.getChinaName().equals(entity.getComment())) {
                entity.setComment(null);
            }
            entity.setName(NameUtil.getFieldHumpName(entity.getFieldName()));
            entity.setType(convertType(entity.getFieldType(), entity.getPrecision(),
                entity.getScale()));
        }
    }

}