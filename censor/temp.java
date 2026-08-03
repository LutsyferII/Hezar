package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelWere extends ModelBase {
  ModelRenderer Head;
  
  ModelRenderer Nose;
  
  ModelRenderer Snout;
  
  ModelRenderer TeethU;
  
  ModelRenderer TeethL;
  
  ModelRenderer Mouth;
  
  ModelRenderer LEar;
  
  ModelRenderer REar;
  
  ModelRenderer Neck;
  
  ModelRenderer Neck2;
  
  ModelRenderer SideburnL;
  
  ModelRenderer SideburnR;
  
  ModelRenderer Chest;
  
  ModelRenderer Abdomen;
  
  ModelRenderer TailA;
  
  ModelRenderer TailC;
  
  ModelRenderer TailB;
  
  ModelRenderer TailD;
  
  ModelRenderer RLegA;
  
  ModelRenderer RFoot;
  
  ModelRenderer RLegB;
  
  ModelRenderer RLegC;
  
  ModelRenderer LLegB;
  
  ModelRenderer LFoot;
  
  ModelRenderer LLegC;
  
  ModelRenderer LLegA;
  
  ModelRenderer RArmB;
  
  ModelRenderer RArmC;
  
  ModelRenderer LArmB;
  
  ModelRenderer RHand;
  
  ModelRenderer RArmA;
  
  ModelRenderer LArmA;
  
  ModelRenderer LArmC;
  
  ModelRenderer LHand;
  
  ModelRenderer RFinger1;
  
  ModelRenderer RFinger2;
  
  ModelRenderer RFinger3;
  
  ModelRenderer RFinger4;
  
  ModelRenderer RFinger5;
  
  ModelRenderer LFinger1;
  
  ModelRenderer LFinger2;
  
  ModelRenderer LFinger3;
  
  ModelRenderer LFinger4;
  
  ModelRenderer LFinger5;
  
  public boolean hunched;
  
  public MoCModelWere() {
    this.field_78090_t = 64;
    this.field_78089_u = 128;
    this.Head = new ModelRenderer(this, 0, 0);
    this.Head.func_78789_a(-4.0F, -3.0F, -6.0F, 8, 8, 6);
    this.Head.func_78793_a(0.0F, -8.0F, -6.0F);
    this.Head.func_78787_b(64, 128);
    this.Nose = new ModelRenderer(this, 44, 33);
    this.Nose.func_78789_a(-1.5F, -1.7F, -12.3F, 3, 2, 7);
    this.Nose.func_78793_a(0.0F, -8.0F, -6.0F);
    setRotation(this.Nose, 0.2792527F, 0.0F, 0.0F);
    this.Snout = new ModelRenderer(this, 0, 25);
    this.Snout.func_78789_a(-2.0F, 2.0F, -12.0F, 4, 2, 6);
    this.Snout.func_78793_a(0.0F, -8.0F, -6.0F);
    this.TeethU = new ModelRenderer(this, 46, 18);
    this.TeethU.func_78789_a(-2.0F, 4.01F, -12.0F, 4, 2, 5);
    this.TeethU.func_78793_a(0.0F, -8.0F, -6.0F);
    this.TeethL = new ModelRenderer(this, 20, 109);
    this.TeethL.func_78789_a(-1.5F, -12.5F, 2.01F, 3, 5, 2);
    this.TeethL.func_78793_a(0.0F, -8.0F, -6.0F);
    setRotation(this.TeethL, 2.530727F, 0.0F, 0.0F);
    this.Mouth = new ModelRenderer(this, 42, 69);
    this.Mouth.func_78789_a(-1.5F, -12.5F, 0.0F, 3, 9, 2);
    this.Mouth.func_78793_a(0.0F, -8.0F, -6.0F);
    setRotation(this.Mouth, 2.530727F, 0.0F, 0.0F);
    this.LEar = new ModelRenderer(this, 13, 14);
    this.LEar.func_78789_a(0.5F, -7.5F, -1.0F, 3, 5, 1);
    this.LEar.func_78793_a(0.0F, -8.0F, -6.0F);
    setRotation(this.LEar, 0.0F, 0.0F, 0.1745329F);
    this.REar = new ModelRenderer(this, 22, 0);
    this.REar.func_78789_a(-3.5F, -7.5F, -1.0F, 3, 5, 1);
    this.REar.func_78793_a(0.0F, -8.0F, -6.0F);
    setRotation(this.REar, 0.0F, 0.0F, -0.1745329F);
    this.Neck = new ModelRenderer(this, 28, 0);
    this.Neck.func_78789_a(-3.5F, -3.0F, -7.0F, 7, 8, 7);
    this.Neck.func_78793_a(0.0F, -5.0F, -2.0F);
    setRotation(this.Neck, -0.6025001F, 0.0F, 0.0F);
    this.Neck2 = new ModelRenderer(this, 0, 14);
    this.Neck2.func_78789_a(-1.5F, -2.0F, -5.0F, 3, 4, 7);
    this.Neck2.func_78793_a(0.0F, -1.0F, -6.0F);
    setRotation(this.Neck2, -0.4537856F, 0.0F, 0.0F);
    this.SideburnL = new ModelRenderer(this, 28, 33);
    this.SideburnL.func_78789_a(3.0F, 0.0F, -2.0F, 2, 6, 6);
    this.SideburnL.func_78793_a(0.0F, -8.0F, -6.0F);
    setRotation(this.SideburnL, -0.2094395F, 0.418879F, -0.0872665F);
    this.SideburnR = new ModelRenderer(this, 28, 45);
    this.SideburnR.func_78789_a(-5.0F, 0.0F, -2.0F, 2, 6, 6);
    this.SideburnR.func_78793_a(0.0F, -8.0F, -6.0F);
    setRotation(this.SideburnR, -0.2094395F, -0.418879F, 0.0872665F);
    this.Chest = new ModelRenderer(this, 20, 15);
    this.Chest.func_78789_a(-4.0F, 0.0F, -7.0F, 8, 8, 10);
    this.Chest.func_78793_a(0.0F, -6.0F, -2.5F);
    setRotation(this.Chest, 0.641331F, 0.0F, 0.0F);
    this.Abdomen = new ModelRenderer(this, 0, 40);
    this.Abdomen.func_78789_a(-3.0F, -8.0F, -8.0F, 6, 14, 8);
    this.Abdomen.func_78793_a(0.0F, 4.5F, 5.0F);
    setRotation(this.Abdomen, 0.2695449F, 0.0F, 0.0F);
    this.TailA = new ModelRenderer(this, 52, 42);
    this.TailA.func_78789_a(-1.5F, -1.0F, -2.0F, 3, 4, 3);
    this.TailA.func_78793_a(0.0F, 9.5F, 6.0F);
    setRotation(this.TailA, 1.064651F, 0.0F, 0.0F);
    this.TailC = new ModelRenderer(this, 48, 59);
    this.TailC.func_78789_a(-2.0F, 6.8F, -4.6F, 4, 6, 4);
    this.TailC.func_78793_a(0.0F, 9.5F, 6.0F);
    setRotation(this.TailC, 1.099557F, 0.0F, 0.0F);
    this.TailB = new ModelRenderer(this, 48, 49);
    this.TailB.func_78789_a(-2.0F, 2.0F, -2.0F, 4, 6, 4);
    this.TailB.func_78793_a(0.0F, 9.5F, 6.0F);
    setRotation(this.TailB, 0.7504916F, 0.0F, 0.0F);
    this.TailD = new ModelRenderer(this, 52, 69);
    this.TailD.func_78789_a(-1.5F, 9.8F, -4.1F, 3, 5, 3);
    this.TailD.func_78793_a(0.0F, 9.5F, 6.0F);
    setRotation(this.TailD, 1.099557F, 0.0F, 0.0F);
    this.RLegA = new ModelRenderer(this, 12, 64);
    this.RLegA.func_78789_a(-2.5F, -1.5F, -3.5F, 3, 8, 5);
    this.RLegA.func_78793_a(-3.0F, 9.5F, 3.0F);
    setRotation(this.RLegA, -0.8126625F, 0.0F, 0.0F);
    this.RFoot = new ModelRenderer(this, 14, 93);
    this.RFoot.func_78789_a(-2.506667F, 12.5F, -5.0F, 3, 2, 3);
    this.RFoot.func_78793_a(-3.0F, 9.5F, 3.0F);
    this.RLegB = new ModelRenderer(this, 14, 76);
    this.RLegB.func_78789_a(-1.9F, 4.2F, 0.5F, 2, 2, 5);
    this.RLegB.func_78793_a(-3.0F, 9.5F, 3.0F);
    setRotation(this.RLegB, -0.8445741F, 0.0F, 0.0F);
    this.RLegC = new ModelRenderer(this, 14, 83);
    this.RLegC.func_78789_a(-2.0F, 6.2F, 0.5F, 2, 8, 2);
    this.RLegC.func_78793_a(-3.0F, 9.5F, 3.0F);
    setRotation(this.RLegC, -0.2860688F, 0.0F, 0.0F);
    this.LLegB = new ModelRenderer(this, 0, 76);
    this.LLegB.func_78789_a(-0.1F, 4.2F, 0.5F, 2, 2, 5);
    this.LLegB.func_78793_a(3.0F, 9.5F, 3.0F);
    setRotation(this.LLegB, -0.8445741F, 0.0F, 0.0F);
    this.LFoot = new ModelRenderer(this, 0, 93);
    this.LFoot.func_78789_a(-0.5066667F, 12.5F, -5.0F, 3, 2, 3);
    this.LFoot.func_78793_a(3.0F, 9.5F, 3.0F);
    this.LLegC = new ModelRenderer(this, 0, 83);
    this.LLegC.func_78789_a(0.0F, 6.2F, 0.5F, 2, 8, 2);
    this.LLegC.func_78793_a(3.0F, 9.5F, 3.0F);
    setRotation(this.LLegC, -0.2860688F, 0.0F, 0.0F);
    this.LLegA = new ModelRenderer(this, 0, 64);
    this.LLegA.func_78789_a(-0.5F, -1.5F, -3.5F, 3, 8, 5);
    this.LLegA.func_78793_a(3.0F, 9.5F, 3.0F);
    setRotation(this.LLegA, -0.8126625F, 0.0F, 0.0F);
    this.RArmB = new ModelRenderer(this, 48, 77);
    this.RArmB.func_78789_a(-3.5F, 1.0F, -1.5F, 4, 8, 4);
    this.RArmB.func_78793_a(-4.0F, -4.0F, -2.0F);
    setRotation(this.RArmB, 0.2617994F, 0.0F, 0.3490659F);
    this.RArmC = new ModelRenderer(this, 48, 112);
    this.RArmC.func_78789_a(-6.0F, 5.0F, 3.0F, 4, 7, 4);
    this.RArmC.func_78793_a(-4.0F, -4.0F, -2.0F);
    setRotation(this.RArmC, -0.3490659F, 0.0F, 0.0F);
    this.LArmB = new ModelRenderer(this, 48, 89);
    this.LArmB.func_78789_a(-0.5F, 1.0F, -1.5F, 4, 8, 4);
    this.LArmB.func_78793_a(4.0F, -4.0F, -2.0F);
    setRotation(this.LArmB, 0.2617994F, 0.0F, -0.3490659F);
    this.RHand = new ModelRenderer(this, 32, 118);
    this.RHand.func_78789_a(-6.0F, 12.5F, -1.5F, 4, 3, 4);
    this.RHand.func_78793_a(-4.0F, -4.0F, -2.0F);
    this.RArmA = new ModelRenderer(this, 0, 108);
    this.RArmA.func_78789_a(-5.0F, -3.0F, -2.0F, 5, 5, 5);
    this.RArmA.func_78793_a(-4.0F, -4.0F, -2.0F);
    setRotation(this.RArmA, 0.6320364F, 0.0F, 0.0F);
    this.LArmA = new ModelRenderer(this, 0, 98);
    this.LArmA.func_78789_a(0.0F, -3.0F, -2.0F, 5, 5, 5);
    this.LArmA.func_78793_a(4.0F, -4.0F, -2.0F);
    setRotation(this.LArmA, 0.6320364F, 0.0F, 0.0F);
    this.LArmC = new ModelRenderer(this, 48, 101);
    this.LArmC.func_78789_a(2.0F, 5.0F, 3.0F, 4, 7, 4);
    this.LArmC.func_78793_a(4.0F, -4.0F, -2.0F);
    setRotation(this.LArmC, -0.3490659F, 0.0F, 0.0F);
    this.LHand = new ModelRenderer(this, 32, 111);
    this.LHand.func_78789_a(2.0F, 12.5F, -1.5F, 4, 3, 4);
    this.LHand.func_78793_a(4.0F, -4.0F, -2.0F);
    this.RFinger1 = new ModelRenderer(this, 8, 120);
    this.RFinger1.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 3, 1);
    this.RFinger1.func_78793_a(-6.5F, 11.5F, -0.5F);
    this.RFinger1 = new ModelRenderer(this, 8, 120);
    this.RFinger1.func_78789_a(-3.0F, 15.5F, 1.0F, 1, 3, 1);
    this.RFinger1.func_78793_a(-4.0F, -4.0F, -2.0F);
    this.RFinger2 = new ModelRenderer(this, 12, 124);
    this.RFinger2.func_78789_a(-3.5F, 15.5F, -1.5F, 1, 3, 1);
    this.RFinger2.func_78793_a(-4.0F, -4.0F, -2.0F);
    this.RFinger3 = new ModelRenderer(this, 12, 119);
    this.RFinger3.func_78789_a(-4.8F, 15.5F, -1.5F, 1, 4, 1);
    this.RFinger3.func_78793_a(-4.0F, -4.0F, -2.0F);
    this.RFinger4 = new ModelRenderer(this, 16, 119);
    this.RFinger4.func_78789_a(-6.0F, 15.5F, -0.5F, 1, 4, 1);
    this.RFinger4.func_78793_a(-4.0F, -4.0F, -2.0F);
    this.RFinger5 = new ModelRenderer(this, 16, 124);
    this.RFinger5.func_78789_a(-6.0F, 15.5F, 1.0F, 1, 3, 1);
    this.RFinger5.func_78793_a(-4.0F, -4.0F, -2.0F);
    this.LFinger1 = new ModelRenderer(this, 8, 124);
    this.LFinger1.func_78789_a(2.0F, 15.5F, 1.0F, 1, 3, 1);
    this.LFinger1.func_78793_a(4.0F, -4.0F, -2.0F);
    this.LFinger2 = new ModelRenderer(this, 0, 124);
    this.LFinger2.func_78789_a(2.5F, 15.5F, -1.5F, 1, 3, 1);
    this.LFinger2.func_78793_a(4.0F, -4.0F, -2.0F);
    this.LFinger3 = new ModelRenderer(this, 0, 119);
    this.LFinger3.func_78789_a(3.8F, 15.5F, -1.5F, 1, 4, 1);
    this.LFinger3.func_78793_a(4.0F, -4.0F, -2.0F);
    this.LFinger4 = new ModelRenderer(this, 4, 119);
    this.LFinger4.func_78789_a(5.0F, 15.5F, -0.5F, 1, 4, 1);
    this.LFinger4.func_78793_a(4.0F, -4.0F, -2.0F);
    this.LFinger5 = new ModelRenderer(this, 4, 124);
    this.LFinger5.func_78789_a(5.0F, 15.5F, 1.0F, 1, 3, 1);
    this.LFinger5.func_78793_a(4.0F, -4.0F, -2.0F);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    setRotationAngles(f, f1, f2, f3, f4, f5);
    this.Head.func_78785_a(f5);
    this.Nose.func_78785_a(f5);
    this.Snout.func_78785_a(f5);
    this.TeethU.func_78785_a(f5);
    this.TeethL.func_78785_a(f5);
    this.Mouth.func_78785_a(f5);
    this.LEar.func_78785_a(f5);
    this.REar.func_78785_a(f5);
    this.Neck.func_78785_a(f5);
    this.Neck2.func_78785_a(f5);
    this.SideburnL.func_78785_a(f5);
    this.SideburnR.func_78785_a(f5);
    this.Chest.func_78785_a(f5);
    this.Abdomen.func_78785_a(f5);
    this.TailA.func_78785_a(f5);
    this.TailC.func_78785_a(f5);
    this.TailB.func_78785_a(f5);
    this.TailD.func_78785_a(f5);
    this.RLegA.func_78785_a(f5);
    this.RFoot.func_78785_a(f5);
    this.RLegB.func_78785_a(f5);
    this.RLegC.func_78785_a(f5);
    this.LLegB.func_78785_a(f5);
    this.LFoot.func_78785_a(f5);
    this.LLegC.func_78785_a(f5);
    this.LLegA.func_78785_a(f5);
    this.RArmB.func_78785_a(f5);
    this.RArmC.func_78785_a(f5);
    this.LArmB.func_78785_a(f5);
    this.RHand.func_78785_a(f5);
    this.RArmA.func_78785_a(f5);
    this.LArmA.func_78785_a(f5);
    this.LArmC.func_78785_a(f5);
    this.LHand.func_78785_a(f5);
    this.RFinger1.func_78785_a(f5);
    this.RFinger2.func_78785_a(f5);
    this.RFinger3.func_78785_a(f5);
    this.RFinger4.func_78785_a(f5);
    this.RFinger5.func_78785_a(f5);
    this.LFinger1.func_78785_a(f5);
    this.LFinger2.func_78785_a(f5);
    this.LFinger3.func_78785_a(f5);
    this.LFinger4.func_78785_a(f5);
    this.LFinger5.func_78785_a(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.field_78795_f = x;
    model.field_78796_g = y;
    model.field_78808_h = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
    float radianF = 57.29578F;
    float RLegXRot = MathHelper.func_76134_b(f * 0.6662F + 3.141593F) * 0.8F * f1;
    float LLegXRot = MathHelper.func_76134_b(f * 0.6662F) * 0.8F * f1;
    this.Head.field_78796_g = f3 / radianF;
    if (!this.hunched) {
      this.Head.field_78797_d = -8.0F;
      this.Head.field_78798_e = -6.0F;
      this.Head.field_78795_f = f4 / radianF;
      this.Neck.field_78795_f = -34.0F / radianF;
      this.Neck.field_78797_d = -5.0F;
      this.Neck.field_78798_e = -2.0F;
      this.Neck2.field_78797_d = -1.0F;
      this.Neck2.field_78798_e = -6.0F;
      this.Chest.field_78797_d = -6.0F;
      this.Chest.field_78798_e = -2.5F;
      this.Chest.field_78795_f = 36.0F / radianF;
      this.Abdomen.field_78795_f = 15.0F / radianF;
      this.LLegA.field_78798_e = 3.0F;
      this.LArmA.field_78797_d = -4.0F;
      this.LArmA.field_78798_e = -2.0F;
      this.TailA.field_78797_d = 9.5F;
      this.TailA.field_78798_e = 6.0F;
    } else {
      this.Head.field_78797_d = 0.0F;
      this.Head.field_78798_e = -11.0F;
      this.Head.field_78795_f = (15.0F + f4) / radianF;
      this.Neck.field_78795_f = -10.0F / radianF;
      this.Neck.field_78797_d = 2.0F;
      this.Neck.field_78798_e = -6.0F;
      this.Neck2.field_78797_d = 9.0F;
      this.Neck2.field_78798_e = -9.0F;
      this.Chest.field_78797_d = 1.0F;
      this.Chest.field_78798_e = -7.5F;
      this.Chest.field_78795_f = 60.0F / radianF;
      this.Abdomen.field_78795_f = 75.0F / radianF;
      this.LLegA.field_78798_e = 7.0F;
      this.LArmA.field_78797_d = 4.5F;
      this.LArmA.field_78798_e = -6.0F;
      this.TailA.field_78797_d = 7.5F;
      this.TailA.field_78798_e = 10.0F;
    } 
    this.Nose.field_78797_d = this.Head.field_78797_d;
    this.Snout.field_78797_d = this.Head.field_78797_d;
    this.TeethU.field_78797_d = this.Head.field_78797_d;
    this.LEar.field_78797_d = this.Head.field_78797_d;
    this.REar.field_78797_d = this.Head.field_78797_d;
    this.TeethL.field_78797_d = this.Head.field_78797_d;
    this.Mouth.field_78797_d = this.Head.field_78797_d;
    this.SideburnL.field_78797_d = this.Head.field_78797_d;
    this.SideburnR.field_78797_d = this.Head.field_78797_d;
    this.Nose.field_78798_e = this.Head.field_78798_e;
    this.Snout.field_78798_e = this.Head.field_78798_e;
    this.TeethU.field_78798_e = this.Head.field_78798_e;
    this.LEar.field_78798_e = this.Head.field_78798_e;
    this.REar.field_78798_e = this.Head.field_78798_e;
    this.TeethL.field_78798_e = this.Head.field_78798_e;
    this.Mouth.field_78798_e = this.Head.field_78798_e;
    this.SideburnL.field_78798_e = this.Head.field_78798_e;
    this.SideburnR.field_78798_e = this.Head.field_78798_e;
    this.LArmB.field_78797_d = this.LArmA.field_78797_d;
    this.LArmC.field_78797_d = this.LArmA.field_78797_d;
    this.LHand.field_78797_d = this.LArmA.field_78797_d;
    this.LFinger1.field_78797_d = this.LArmA.field_78797_d;
    this.LFinger2.field_78797_d = this.LArmA.field_78797_d;
    this.LFinger3.field_78797_d = this.LArmA.field_78797_d;
    this.LFinger4.field_78797_d = this.LArmA.field_78797_d;
    this.LFinger5.field_78797_d = this.LArmA.field_78797_d;
    this.RArmA.field_78797_d = this.LArmA.field_78797_d;
    this.RArmB.field_78797_d = this.LArmA.field_78797_d;
    this.RArmC.field_78797_d = this.LArmA.field_78797_d;
    this.RHand.field_78797_d = this.LArmA.field_78797_d;
    this.RFinger1.field_78797_d = this.LArmA.field_78797_d;
    this.RFinger2.field_78797_d = this.LArmA.field_78797_d;
    this.RFinger3.field_78797_d = this.LArmA.field_78797_d;
    this.RFinger4.field_78797_d = this.LArmA.field_78797_d;
    this.RFinger5.field_78797_d = this.LArmA.field_78797_d;
    this.LArmB.field_78798_e = this.LArmA.field_78798_e;
    this.LArmC.field_78798_e = this.LArmA.field_78798_e;
    this.LHand.field_78798_e = this.LArmA.field_78798_e;
    this.LFinger1.field_78798_e = this.LArmA.field_78798_e;
    this.LFinger2.field_78798_e = this.LArmA.field_78798_e;
    this.LFinger3.field_78798_e = this.LArmA.field_78798_e;
    this.LFinger4.field_78798_e = this.LArmA.field_78798_e;
    this.LFinger5.field_78798_e = this.LArmA.field_78798_e;
    this.RArmA.field_78798_e = this.LArmA.field_78798_e;
    this.RArmB.field_78798_e = this.LArmA.field_78798_e;
    this.RArmC.field_78798_e = this.LArmA.field_78798_e;
    this.RHand.field_78798_e = this.LArmA.field_78798_e;
    this.RFinger1.field_78798_e = this.LArmA.field_78798_e;
    this.RFinger2.field_78798_e = this.LArmA.field_78798_e;
    this.RFinger3.field_78798_e = this.LArmA.field_78798_e;
    this.RFinger4.field_78798_e = this.LArmA.field_78798_e;
    this.RFinger5.field_78798_e = this.LArmA.field_78798_e;
    this.RLegA.field_78798_e = this.LLegA.field_78798_e;
    this.RLegB.field_78798_e = this.LLegA.field_78798_e;
    this.RLegC.field_78798_e = this.LLegA.field_78798_e;
    this.RFoot.field_78798_e = this.LLegA.field_78798_e;
    this.LLegB.field_78798_e = this.LLegA.field_78798_e;
    this.LLegC.field_78798_e = this.LLegA.field_78798_e;
    this.LFoot.field_78798_e = this.LLegA.field_78798_e;
    this.TailB.field_78797_d = this.TailA.field_78797_d;
    this.TailB.field_78798_e = this.TailA.field_78798_e;
    this.TailC.field_78797_d = this.TailA.field_78797_d;
    this.TailC.field_78798_e = this.TailA.field_78798_e;
    this.TailD.field_78797_d = this.TailA.field_78797_d;
    this.TailD.field_78798_e = this.TailA.field_78798_e;
    this.Nose.field_78796_g = this.Head.field_78796_g;
    this.Snout.field_78796_g = this.Head.field_78796_g;
    this.TeethU.field_78796_g = this.Head.field_78796_g;
    this.LEar.field_78796_g = this.Head.field_78796_g;
    this.REar.field_78796_g = this.Head.field_78796_g;
    this.TeethL.field_78796_g = this.Head.field_78796_g;
    this.Mouth.field_78796_g = this.Head.field_78796_g;
    this.Head.field_78795_f += 2.530727F;
    this.Head.field_78795_f += 2.530727F;
    this.SideburnL.field_78795_f = -0.2094395F + this.Head.field_78795_f;
    this.SideburnL.field_78796_g = 0.418879F + this.Head.field_78796_g;
    this.SideburnR.field_78795_f = -0.2094395F + this.Head.field_78795_f;
    this.SideburnR.field_78796_g = -0.418879F + this.Head.field_78796_g;
    this.Nose.field_78795_f = 0.2792527F + this.Head.field_78795_f;
    this.Snout.field_78795_f = this.Head.field_78795_f;
    this.TeethU.field_78795_f = this.Head.field_78795_f;
    this.LEar.field_78795_f = this.Head.field_78795_f;
    this.REar.field_78795_f = this.Head.field_78795_f;
    this.RLegA.field_78795_f = -0.8126625F + RLegXRot;
    this.RLegB.field_78795_f = -0.8445741F + RLegXRot;
    this.RLegC.field_78795_f = -0.2860688F + RLegXRot;
    this.RFoot.field_78795_f = RLegXRot;
    this.LLegA.field_78795_f = -0.8126625F + LLegXRot;
    this.LLegB.field_78795_f = -0.8445741F + LLegXRot;
    this.LLegC.field_78795_f = -0.2860688F + LLegXRot;
    this.LFoot.field_78795_f = LLegXRot;
    this.RArmA.field_78808_h = -(MathHelper.func_76134_b(f2 * 0.09F) * 0.05F) + 0.05F;
    this.LArmA.field_78808_h = MathHelper.func_76134_b(f2 * 0.09F) * 0.05F - 0.05F;
    this.RArmA.field_78795_f = LLegXRot;
    this.LArmA.field_78795_f = RLegXRot;
    this.RArmB.field_78808_h = 0.3490659F + this.RArmA.field_78808_h;
    this.LArmB.field_78808_h = -0.3490659F + this.LArmA.field_78808_h;
    this.RArmB.field_78795_f = 0.2617994F + this.RArmA.field_78795_f;
    this.LArmB.field_78795_f = 0.2617994F + this.LArmA.field_78795_f;
    this.RArmC.field_78808_h = this.RArmA.field_78808_h;
    this.LArmC.field_78808_h = this.LArmA.field_78808_h;
    this.RArmC.field_78795_f = -0.3490659F + this.RArmA.field_78795_f;
    this.LArmC.field_78795_f = -0.3490659F + this.LArmA.field_78795_f;
    this.RHand.field_78808_h = this.RArmA.field_78808_h;
    this.LHand.field_78808_h = this.LArmA.field_78808_h;
    this.RHand.field_78795_f = this.RArmA.field_78795_f;
    this.LHand.field_78795_f = this.LArmA.field_78795_f;
    this.RFinger1.field_78795_f = this.RArmA.field_78795_f;
    this.RFinger2.field_78795_f = this.RArmA.field_78795_f;
    this.RFinger3.field_78795_f = this.RArmA.field_78795_f;
    this.RFinger4.field_78795_f = this.RArmA.field_78795_f;
    this.RFinger5.field_78795_f = this.RArmA.field_78795_f;
    this.LFinger1.field_78795_f = this.LArmA.field_78795_f;
    this.LFinger2.field_78795_f = this.LArmA.field_78795_f;
    this.LFinger3.field_78795_f = this.LArmA.field_78795_f;
    this.LFinger4.field_78795_f = this.LArmA.field_78795_f;
    this.LFinger5.field_78795_f = this.LArmA.field_78795_f;
    this.RFinger1.field_78808_h = this.RArmA.field_78808_h;
    this.RFinger2.field_78808_h = this.RArmA.field_78808_h;
    this.RFinger3.field_78808_h = this.RArmA.field_78808_h;
    this.RFinger4.field_78808_h = this.RArmA.field_78808_h;
    this.RFinger5.field_78808_h = this.RArmA.field_78808_h;
    this.LFinger1.field_78808_h = this.LArmA.field_78808_h;
    this.LFinger2.field_78808_h = this.LArmA.field_78808_h;
    this.LFinger3.field_78808_h = this.LArmA.field_78808_h;
    this.LFinger4.field_78808_h = this.LArmA.field_78808_h;
    this.LFinger5.field_78808_h = this.LArmA.field_78808_h;
  }
}
