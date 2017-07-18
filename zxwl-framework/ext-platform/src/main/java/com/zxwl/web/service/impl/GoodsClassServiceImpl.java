package com.zxwl.web.service.impl;

import com.zxwl.web.bean.GoodsClass;
import com.zxwl.web.bean.common.QueryParam;
import com.zxwl.web.bean.tree.GoodsClassTree;
import com.zxwl.web.dao.GoodsClassMapper;
import com.zxwl.web.service.GoodsClassService;
import com.zxwl.web.service.QueryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品类别 服务类实现
 * Created by generator
 */
@Service("goodsClassService")
public class GoodsClassServiceImpl extends AbstractServiceImpl<GoodsClass, String> implements GoodsClassService {

    @Resource
    protected GoodsClassMapper goodsClassMapper;

    @Override
    protected GoodsClassMapper getMapper() {
        return this.goodsClassMapper;
    }

    @Override
    public String insert(GoodsClass data) {
        return super.insert(data);
    }

    @Override
    public int update(GoodsClass data) {
        return super.update(data);
    }

    @Override
    public int update(List<GoodsClass> data) {
        return super.update(data);
    }

    /**
     * 树分段加载用
     * 根据 classCode 查询该树下的节点
     *
     * @param classCode
     * @return
     */
    @Override
    public GoodsClassTree getTreeNodeByClassCode(String classCode) {

        GoodsClassTree tree = new GoodsClassTree();

        GoodsClass goodsClass = QueryService.createQuery(goodsClassMapper)
                .where(GoodsClass.Property.classCode, classCode)
                .single();
        if (goodsClass != null) {

            tree.setId(goodsClass.getId());
            tree.setClassCode(goodsClass.getClassCode());
            tree.setClassName(goodsClass.getClassName());
            tree.setParentCode(goodsClass.getParentCode());

            List<GoodsClass> list = QueryService.createQuery(goodsClassMapper)
                    .where(GoodsClass.Property.parentCode, classCode)
                    .list();

            recursiveTree(list, tree);
        }

        return tree;
    }

    /**
     * 获取商品类型树
     *
     * @return GoodsClassTrees
     */
    @Override
    public List<GoodsClassTree> getTreeNodes() {
        List<GoodsClass> list = QueryService.createQuery(goodsClassMapper).list();
        return buildByRecursive(list);
    }

    private static void recursiveTree(List<GoodsClass> list, GoodsClassTree tree) {

        for (GoodsClass goodsClass : list) {
            GoodsClassTree children = new GoodsClassTree();
            children.setId(goodsClass.getId());
            children.setClassName(goodsClass.getClassName());
            children.setClassCode(goodsClass.getClassCode());
            children.setParentCode(goodsClass.getParentCode());

            tree.getNodes().add(children);
        }
    }


    private static List<GoodsClassTree> buildByRecursive(List<GoodsClass> list) {
        List<GoodsClassTree> trees = new ArrayList<>();
        for (GoodsClass gc : list) {
            if ("".equals(gc.getParentCode()) || gc.getParentCode() == null) {
                GoodsClassTree o = new GoodsClassTree(gc.getId(), gc.getClassCode(),
                        gc.getClassName(), gc.getParentCode());
                trees.add(findChildren(o, list));
            }
        }
        return trees;
    }

    private static GoodsClassTree findChildren(GoodsClassTree tree, List<GoodsClass> list) {

        for (GoodsClass gc : list) {
            if (gc.getParentCode() != null && gc.getParentCode().equals(tree.getClassCode())) {
                GoodsClassTree o = new GoodsClassTree(gc.getId(), gc.getClassCode(),
                        gc.getClassName(), gc.getParentCode());

                if (tree.getNodes() == null) {
                    tree.setNodes(new ArrayList<GoodsClassTree>());
                }

                tree.getNodes().add(findChildren(o, list));
            }
        }

        return tree;
    }
}
