package net.dark.pierced.entity.custom;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.dark.pierced.item.ModItems;
import net.dark.pierced.mixin.PersistentProjectileEntityAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HarpoonEntity extends PersistentProjectileEntity {

    private int life;
    protected void age() {
        this.life++;
        if (this.life >= 500) {
            this.discard();
        }
    }
    public HarpoonEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        TrackedData<Byte> pierceData = ((PersistentProjectileEntityAccessor) this).getPierceLevelTrackedData();

        this.dataTracker.set(pierceData, (byte) (1 + this.getPierceLevel()));
    }
    boolean hitBlock =false;
    Vec3d blockLocation;
    private BlockState inBlockState;
    @Nullable
    private IntOpenHashSet piercedEntities;
    Boolean SendingBack =false;
    @Nullable
    private List<Entity> piercingKilledEntities;


    private boolean shouldFall() {
        return this.inGround && this.getWorld().isSpaceEmpty(new Box(this.getPos(), this.getPos()).expand(0.06));
    }
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity hitEntity = entityHitResult.getEntity();


        if (hitEntity == this.getOwner() && hitEntity instanceof PlayerEntity player) {
            if (!this.getWorld().isClient()) {
                ItemStack harpoonStack = new ItemStack(ModItems.HARPOON);

                boolean addedToInventory = player.getInventory().insertStack(harpoonStack);
                if (!addedToInventory) {
                    ItemEntity droppedHarpoon = new ItemEntity(
                            player.getWorld(),
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            harpoonStack
                    );

                    player.getWorld().spawnEntity(droppedHarpoon);
                }
            }
            this.discard();
            return;
        }

        super.onEntityHit(entityHitResult);
    }
    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (!this.getWorld().isClient && (this.inGround || this.isNoClip()) && this.shake <= 0) {
            if (this.tryPickup(player)) {
                player.sendPickup(this, 1);
                this.discard();
            }
        }
    }




    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

    }


    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        if (getOwner()!=null) {
            var owner = getOwner();
            assert owner != null;
            var difference = owner.getPos().subtract(target.getPos());
            var distance = Math.sqrt(Math.pow(difference.x, 2) + Math.pow(difference.y, 2) + Math.pow(difference.z, 2));
            double scaleFactor = Math.pow(distance, 0.6);
            target.addVelocity(difference.x / scaleFactor, difference.y / scaleFactor, difference.z / scaleFactor);

            this.setVelocity(0,0,0);
            SendingBack=true;
            this.inGround = true;
        }
    }


    private void clearPiercingStatus() {
        if (this.piercingKilledEntities != null) {
            this.piercingKilledEntities.clear();
        }

        if (this.piercedEntities != null) {
            this.piercedEntities.clear();
        }
    }
    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (getOwner()!=null) {
            hitBlock = true;

        }

    }


    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

    }
    @Override
    public void tick() {
        if (getOwner() != null) {
            var owner = getOwner();
            var targetPos = this.getPos();
            var currentPos = owner.getPos().add(0,owner.getEyeHeight(owner.getPose()),0);
            var velocity = owner.getVelocity();
            var relativePos = targetPos.subtract(currentPos);
            double distance = relativePos.length();
            if (distance>85 && !hitBlock){
                SendingBack=true;
            }
            if (hitBlock) {
                double stiffness = 0.15;
                double damping = 0.9;
                double minDistance = 0.02;
                double maxSpeed = 0.3;
                double farThreshold = 5.0;


                Vec3d idealVelocity;
                if (distance > farThreshold) {
                    idealVelocity = relativePos.normalize().multiply(maxSpeed);
                } else {
                    idealVelocity = relativePos.multiply(stiffness);
                }
                var newVelocity = velocity.multiply(damping).add(idealVelocity);


                if (distance < minDistance) {
                    newVelocity = new Vec3d(0, 0, 0);
                }


                owner.setVelocity(newVelocity);
            }
        }
        super.tick();
        if (this.getWorld().isClient) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {

                }
            } else {

            }
        } else if (this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) {
            this.getWorld().sendEntityStatus(this, (byte)0);
                this.setStack(new ItemStack(ModItems.HARPOON));
        }
        if (SendingBack){
            if (this.getOwner()!=null) {
                var owner = getOwner();
                assert owner != null;
                var targetPos = owner.getPos().add(0, owner.getEyeHeight(owner.getPose()), 0);
                var difference = targetPos.subtract(this.getPos());

                double k = 0.1;

                this.addVelocity(difference.x * k, difference.y * k, difference.z * k);
            }
        }
    }







    public HarpoonEntity(
            EntityType<? extends PersistentProjectileEntity> type, double x, double y, double z, World world, ItemStack stack, @Nullable ItemStack weapon
    ) {
        super(type,x,y,z,world,stack,weapon);
        TrackedData<Byte> pierceData = ((PersistentProjectileEntityAccessor) this).getPierceLevelTrackedData();

        this.dataTracker.set(pierceData, (byte) (1 + this.getPierceLevel()));


    }


    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ModItems.HARPOON);
    }


    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ModItems.HARPOON);
    }








}

