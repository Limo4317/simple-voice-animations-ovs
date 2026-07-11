package de.maxhenkel.simplevoiceanimations;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.core.Direction;

import java.util.EnumSet;
import java.util.Set;

public class SplitHeadModel {

    public static final String HEAD_TOP = "simplevoiceanimations_head_top";
    public static final String HEAD_BOTTOM = "simplevoiceanimations_head_bottom";
    public static final String HAT_TOP = "simplevoiceanimations_hat_top";
    public static final String HAT_BOTTOM = "simplevoiceanimations_hat_bottom";

    private static final Set<Direction> ALL_FACES = EnumSet.allOf(Direction.class);
    private static final Set<Direction> SIDE_FACES = EnumSet.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
    private static final Set<Direction> TOP_FACE = EnumSet.of(Direction.DOWN);
    private static final Set<Direction> BOTTOM_FACE = EnumSet.of(Direction.UP);

    /**
     * Adds two hidden half-head parts (plus matching hat overlay halves) to the head,
     * split horizontally at eye level. The top half is hinged at the back of the head,
     * so rotating it around the X axis flips it open like a Pez dispenser.
     * <p>
     * A half-high box with the texture offset shifted down by the box height samples the
     * matching half of the regular texture on its side faces. The top and bottom faces of
     * the bottom half are added as separate flat boxes instead, both sampling the regular
     * bottom texture, so the visible cut face doesn't show the top of the head.
     * <p>
     * Used for both the player model and the helmet armor model, which only differ in
     * their inflation.
     */
    public static void addSplitHeadParts(PartDefinition head, CubeDeformation deformation) {
        PartDefinition headTop = head.addOrReplaceChild(HEAD_TOP, topCubes(0, deformation), PartPose.offset(0F, -4F, 4F));
        headTop.addOrReplaceChild(HAT_TOP, topCubes(32, deformation.extend(0.5F)), PartPose.ZERO);
        PartDefinition headBottom = head.addOrReplaceChild(HEAD_BOTTOM, bottomCubes(0, deformation), PartPose.ZERO);
        headBottom.addOrReplaceChild(HAT_BOTTOM, bottomCubes(32, deformation.extend(0.5F)), PartPose.ZERO);
    }

    private static CubeListBuilder topCubes(int xTexOffs, CubeDeformation deformation) {
        return new SplitCubeListBuilder()
                .box(xTexOffs, 0, -4F, -4F - deformation.growY / 2F, -8F, 8F, 4F, 8F, growHalfVertically(deformation), ALL_FACES);
    }

    private static CubeListBuilder bottomCubes(int xTexOffs, CubeDeformation deformation) {
        return new SplitCubeListBuilder()
                .box(xTexOffs, 4, -4F, -4F + deformation.growY / 2F, -4F, 8F, 4F, 8F, growHalfVertically(deformation), SIDE_FACES)
                .box(xTexOffs, 0, -4F, 0F, -4F, 8F, 0F, 8F, deformation, BOTTOM_FACE)
                .box(xTexOffs + 8, 0, -4F, -4F, -4F, 8F, 0F, 8F, new CubeDeformation(deformation.growX, 0F, deformation.growZ), TOP_FACE);
    }

    /**
     * Half the vertical inflation, combined with shifting the box by the other half, makes
     * the halves only grow away from the split plane, so they meet exactly at the seam
     * instead of overlapping.
     */
    private static CubeDeformation growHalfVertically(CubeDeformation deformation) {
        return new CubeDeformation(deformation.growX, deformation.growY / 2F, deformation.growZ);
    }

    private static class SplitCubeListBuilder extends CubeListBuilder {

        private SplitCubeListBuilder box(int xTexOffs, int yTexOffs, float x, float y, float z, float width, float height, float depth, CubeDeformation deformation, Set<Direction> visibleFaces) {
            cubes.add(new CubeDefinition(null, xTexOffs, yTexOffs, x, y, z, width, height, depth, deformation, false, 1F, 1F, visibleFaces));
            return this;
        }

    }

}
