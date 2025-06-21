package net.dark.pierced.entity.custom;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.dark.pierced.item.ModItems;
import net.dark.pierced.item.custom.HarpoonCrossbow;
import net.dark.pierced.mixin.PersistentProjectileEntityAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
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
import java.util.Objects;

public class HarpoonEntity extends PersistentProjectileEntity {
    private boolean swinging = false;
    private double swingLength = 0.0;

    private int life;
    protected void age() {
        this.life++;
        if (this.life >= 500) {
            ItemEntity droppedHarpoon = new ItemEntity(
                    Objects.requireNonNull(this.getOwner()).getWorld(),
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    this.getItemStack());


            this.getWorld().spawnEntity(droppedHarpoon);
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
//            System.out.println(this.tryPickup(player));
            if (player == this.getOwner()) {
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
            if ((MinecraftClient.getInstance().options.jumpKey.isPressed()&&getOwner().isSneaking())||!getOwner().isAlive()){
                ItemEntity droppedHarpoon = new ItemEntity(
                        this.getOwner().getWorld(),
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        this.getItemStack());


                this.getWorld().spawnEntity(droppedHarpoon);
                this.discard();
            }
            PlayerEntity owner = (PlayerEntity) getOwner();
            var targetPos = this.getPos();

            var currentPos = owner.getPos().subtract(0, 1, 0);
            var relativePos = targetPos.subtract(currentPos);
            if (targetPos.y - owner.getPos().y>0 &&-5<relativePos.x&&5>relativePos.x && -5<relativePos.z && relativePos.z<5) {

                currentPos = owner.getPos().subtract(0, -(targetPos.y - owner.getPos().y)/2, 0);
            }
            var velocity = owner.getVelocity();
            relativePos = targetPos.subtract(currentPos);
            double distance = relativePos.length();
            if (distance>85 && !hitBlock){
                SendingBack=true;
            }
            if (hitBlock) {
                if (owner.isSneaking()) {
                    if (!swinging) {
                        swinging = true;

                        swingLength = distance;
                        owner.setVelocity(Vec3d.ZERO);
                    } else {

                        Vec3d direction = this.getPos().subtract(currentPos);
                        Vec3d norm = direction.normalize();
                        double error = distance - swingLength;
                        double k = 0.1;
                        Vec3d impulse = norm.multiply(error * k);
                        Vec3d modImpulse = new Vec3d(impulse.x * 0.8, impulse.y * 0.8, impulse.z * 0.8);
                        Vec3d gravityDir = new Vec3d(0, -1, 0);
                        Vec3d tanDir = gravityDir.subtract(norm.multiply(gravityDir.dotProduct(norm))).normalize();
                        Vec3d horizontal = new Vec3d(direction.x, 0, direction.z);
                        double horizFactor = horizontal.length() / swingLength;
                        Vec3d swingBoost = tanDir.multiply(horizFactor * 0.4);
                        this.setVelocity(this.getVelocity().subtract(modImpulse));
                        owner.setVelocity(owner.getVelocity().add(modImpulse).add(swingBoost));
                        Vec3d v = owner.getVelocity();
                        owner.setVelocity(new Vec3d(v.x, v.y * 0.9, v.z));
                    }
                } else {
                    swinging = false;
                }
            }
            if (hitBlock&& (!owner.isSneaking())) {
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

                double k = 0.3;

                this.setVelocity(difference.x * k, difference.y * k, difference.z * k);
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

