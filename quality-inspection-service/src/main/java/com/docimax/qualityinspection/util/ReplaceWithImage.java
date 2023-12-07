package com.docimax.qualityinspection.util;

import com.aspose.words.*;

/**
 * @author : lierlin
 * @className : ClientController
 * @description : 客户端(应用)管理
 * @date : 2023/8/11 15:42
 */
public  class ReplaceWithImage implements IReplacingCallback {
    private Shape imageShape;

    public ReplaceWithImage(Shape imageShape) {
        this.imageShape = imageShape;
    }
    @Override
    public int replacing(ReplacingArgs e) throws Exception {
        // 获取当前匹配的节点
        Node currentNode = e.getMatchNode();
        // 获取匹配的 Run 节点
        Run currentRun = (Run) currentNode;

        // 将图片插入到匹配的 Run 节点之前
        currentRun.getParentParagraph().insertBefore(imageShape, currentRun);

        // 删除匹配的 Run 节点
        currentRun.remove();

        return ReplaceAction.SKIP;
    }
}
