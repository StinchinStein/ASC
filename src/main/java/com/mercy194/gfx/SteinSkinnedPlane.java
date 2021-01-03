package com.mercy194.gfx;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.model.PositionTextureVertex;
import net.minecraft.client.renderer.model.TexturedQuad;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SteinSkinnedPlane {
   private final PositionTextureVertex[] vertices;
   private final TexturedQuad[] quads;
   public final float posX1;
   public final float posY1;
   public final float posZ1;
   public final float posX2;
   public final float posZ2;
   public String boxName;

   public SteinSkinnedPlane(int tW, int tH, int texU, int texV, float x, float y, float z, int dx, int dz) {
      this(tW, tH, texU, texV, x, y, z, dx, dz, 1.0f, false);
   }

   public SteinSkinnedPlane(int tW, int tH, int texU, int texV, float x, float y, float z, int dx, int dz, float delta, boolean mirror) {
      this.posX1 = x;
      this.posY1 = y;
      this.posZ1 = z;
      this.posX2 = x + (float)dx;
      this.posZ2 = z + (float)dz;
      this.vertices = new PositionTextureVertex[8];
      this.quads = new TexturedQuad[1];
      float f = x + (float)dx;
      float f2 = z + (float)dz;
      x = x - delta;
      y = y - delta;
      z = z - delta;
      f = f + delta;
      f2 = f2 + delta;
      if (mirror) {
         float f3 = f;
         f = x;
         x = f3;
      }

      PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(x, y, z, 0.0F, 0.0F);
      PositionTextureVertex positiontexturevertex = new PositionTextureVertex(f, y, z, 0.0F, 8.0F);
      PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(x, y, f2, 0.0F, 0.0F);
      PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f, y, f2, 0.0F, 8.0F);
      this.vertices[0] = positiontexturevertex7;
      this.vertices[1] = positiontexturevertex;
      this.vertices[4] = positiontexturevertex3;
      this.vertices[5] = positiontexturevertex4;
      this.quads[0] = new TexturedQuad(new PositionTextureVertex[]{positiontexturevertex4, positiontexturevertex3, positiontexturevertex7, positiontexturevertex}, texU, texV, texU + dz, texV + dz, tW, tH);
      if (mirror) {
         for(TexturedQuad texturedquad : this.quads) {
            texturedquad.flipFace();
         }
      }

   }

   public void render(BufferBuilder renderer, float scale) {
      for(TexturedQuad texturedquad : this.quads) {
         texturedquad.draw(renderer, scale);
      }

   }

   public SteinSkinnedPlane setBoxName(String name) {
      this.boxName = name;
      return this;
   }
}