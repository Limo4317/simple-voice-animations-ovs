package de.maxhenkel.simplevoiceanimations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.List;

public class SplitHeadRenderer {

    private static final float HINGE_Z = 4F;

    public static void render(List<ModelPart.Cube> cubes, PoseStack.Pose pose, VertexConsumer buffer, int lightCoords, int overlayCoords, int color, float splitHeight, float splitRotation, float splitDistance) {
        float planeY = -splitHeight;
        Matrix4f topPose = new Matrix4f(pose.pose())
                .translate(0F, -splitDistance, 0F)
                .translate(0F, planeY / 16F, HINGE_Z / 16F)
                .rotateX(-splitRotation)
                .translate(0F, -planeY / 16F, -HINGE_Z / 16F);
        Matrix3f topNormal = new Matrix3f(pose.normal()).rotateX(-splitRotation);
        for (ModelPart.Cube cube : cubes) {
            renderCube(cube, planeY, pose.pose(), pose.normal(), topPose, topNormal, buffer, lightCoords, overlayCoords, color);
        }
    }

    private static void renderCube(ModelPart.Cube cube, float planeY, Matrix4f bottomPose, Matrix3f bottomNormal, Matrix4f topPose, Matrix3f topNormal, VertexConsumer buffer, int lightCoords, int overlayCoords, int color) {
        ModelPart.Polygon bottomFace = null;
        float minY = Float.POSITIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;
        for (ModelPart.Polygon polygon : cube.polygons) {
            for (ModelPart.Vertex vertex : polygon.vertices()) {
                minY = Math.min(minY, vertex.y());
                maxY = Math.max(maxY, vertex.y());
            }
        }
        for (ModelPart.Polygon polygon : cube.polygons) {
            ModelPart.Vertex[] vertices = polygon.vertices();
            if (polygon.normal().y() != 0F) {
                if (polygon.normal().y() > 0F) {
                    bottomFace = polygon;
                }
                boolean top = vertices[0].y() <= planeY;
                renderQuad(top ? topPose : bottomPose, top ? topNormal : bottomNormal, buffer, vertices, polygon.normal(), lightCoords, overlayCoords, color);
                continue;
            }
            float firstEdgeY = vertices[0].y();
            float secondEdgeY = vertices[2].y();
            if (planeY <= Math.min(firstEdgeY, secondEdgeY)) {
                renderQuad(bottomPose, bottomNormal, buffer, vertices, polygon.normal(), lightCoords, overlayCoords, color);
            } else if (planeY >= Math.max(firstEdgeY, secondEdgeY)) {
                renderQuad(topPose, topNormal, buffer, vertices, polygon.normal(), lightCoords, overlayCoords, color);
            } else {
                float delta = (planeY - firstEdgeY) / (secondEdgeY - firstEdgeY);
                ModelPart.Vertex cut03 = lerpVertex(vertices[0], vertices[3], delta);
                ModelPart.Vertex cut12 = lerpVertex(vertices[1], vertices[2], delta);
                renderQuad(topPose, topNormal, buffer, new ModelPart.Vertex[]{vertices[0], vertices[1], cut12, cut03}, polygon.normal(), lightCoords, overlayCoords, color);
                renderQuad(bottomPose, bottomNormal, buffer, new ModelPart.Vertex[]{cut03, cut12, vertices[2], vertices[3]}, polygon.normal(), lightCoords, overlayCoords, color);
            }
        }
        if (bottomFace != null && planeY > minY && planeY < maxY) {
            ModelPart.Vertex[] vertices = bottomFace.vertices();
            ModelPart.Vertex[] cutFace = new ModelPart.Vertex[]{atHeight(vertices[0], planeY), atHeight(vertices[1], planeY), atHeight(vertices[2], planeY), atHeight(vertices[3], planeY)};
            renderQuad(topPose, topNormal, buffer, cutFace, bottomFace.normal(), lightCoords, overlayCoords, color);
            ModelPart.Vertex[] flippedCutFace = new ModelPart.Vertex[]{cutFace[3], cutFace[2], cutFace[1], cutFace[0]};
            renderQuad(bottomPose, bottomNormal, buffer, flippedCutFace, new Vector3f(bottomFace.normal()).negate(), lightCoords, overlayCoords, color);
        }
    }

    private static void renderQuad(Matrix4f pose, Matrix3f normalMatrix, VertexConsumer buffer, ModelPart.Vertex[] vertices, Vector3fc normal, int lightCoords, int overlayCoords, int color) {
        Vector3f transformedNormal = normalMatrix.transform(new Vector3f(normal)).normalize();
        Vector3f position = new Vector3f();
        for (ModelPart.Vertex vertex : vertices) {
            pose.transformPosition(vertex.x() / 16F, vertex.y() / 16F, vertex.z() / 16F, position);
            buffer.addVertex(position.x(), position.y(), position.z(), color, vertex.u(), vertex.v(), overlayCoords, lightCoords, transformedNormal.x(), transformedNormal.y(), transformedNormal.z());
        }
    }

    private static ModelPart.Vertex lerpVertex(ModelPart.Vertex from, ModelPart.Vertex to, float delta) {
        return new ModelPart.Vertex(Mth.lerp(delta, from.x(), to.x()), Mth.lerp(delta, from.y(), to.y()), Mth.lerp(delta, from.z(), to.z()), Mth.lerp(delta, from.u(), to.u()), Mth.lerp(delta, from.v(), to.v()));
    }

    private static ModelPart.Vertex atHeight(ModelPart.Vertex vertex, float y) {
        return new ModelPart.Vertex(vertex.x(), y, vertex.z(), vertex.u(), vertex.v());
    }

}
