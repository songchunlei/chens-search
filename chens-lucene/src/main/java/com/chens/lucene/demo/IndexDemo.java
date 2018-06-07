package com.chens.lucene.demo;

import com.chens.lucene.ik.IKAnalyzer4Lucene7;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

/**
 * 索引demo
 *
 * @author songchunlei@qq.com
 * @create 2018/5/8
 */
public class IndexDemo {
    public static void main(String[] args) {
        //分词器
        Analyzer analyzer = new IKAnalyzer4Lucene7(true);
        //索引配置对象
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //索引打开方式
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        //存放内存
        //Directory directory = new RAMDirectory();
        try {
            //存放目录
            Directory directory = FSDirectory.open((new File("/opt/upload")).toPath());
            //创建索引写对象
            IndexWriter writer = new IndexWriter(directory,config);
            //创建doc
            Document document = new Document();
            //商品id
            document.add(new StoredField("prodId","p0001"));
            //商品名称字段
            String prodName = "ThinkPad X1 Carbon 20JOIDHDKHAA 超级电脑笔记本";
            document.add(new TextField("name",prodName, Field.Store.YES));
            //将文档添加到索引
            writer.addDocument(document);

            //继续添加其他文档 ///

            //刷新,存储到库里，而不是缓存在内存
            writer.flush();

            //提交
            writer.commit();

            //关闭
            writer.close();

            directory.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
