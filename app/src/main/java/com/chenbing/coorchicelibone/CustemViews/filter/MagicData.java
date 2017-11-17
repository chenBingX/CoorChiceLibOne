package com.chenbing.coorchicelibone.CustemViews.filter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by coorchice on 2017/11/15.
 */

public class MagicData<T> implements Cloneable {

    /**
     * 注意，传入MagicData的数据类必须实现Cloneable接口。
     */
    private T data;
    private boolean state;
    /**
     * 可以直接使用layout的id作为type
     */
    private int type = -99;

    /**
     * 相同的绑定逻辑可以复用同一个实例，这样能节约开支。
     * 在进行Clone时，该成员不会被Clone，而是复用。
     */
    private BindDataLogic<MagicData<T>> bindLogic;
    private Method cloneMethod;
    /**
     * tag被用来保存一些关联、状态或者其它任意的数据。
     * 在进行复杂逻辑处理的时候，它能够帮助你更好、更简便的解决问题。
     */
    private Object tag;

    public MagicData(T data, boolean state, int type) {
        this(data, state, type, null);
    }

    public MagicData(T data, boolean state, int type, BindDataLogic<MagicData<T>> bindLogic) {
        this.data = data;
        this.state = state;
        this.type = type;
        this.bindLogic = bindLogic;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 获取保存在MagicData中的Tag，如果存在的话。
     *
     * tag被用来保存一些关联、状态或者其它任意的数据。
     * 在进行复杂逻辑处理的时候，它能够帮助你更好、更简便的解决问题。
     *
     * @return 可能返回null，需要进行null处理。
     */
    public Object getTag() {
        return tag;
    }

    /**
     * 保存一个tag。
     * tag被用来保存一些关联、状态或者其它任意的数据。
     * 在进行复杂逻辑处理的时候，它能够帮助你更好、更简便的解决问题。
     *
     * @param tag
     */
    public void setTag(Object tag) {
        this.tag = tag;
    }

    /**
     * 获得该数据的绑定逻辑。
     * <p>
     * 相同的绑定逻辑可以复用同一个实例，这样能节约开支。
     * 在进行Clone时，该成员不会被Clone，而是复用。
     */
    public BindDataLogic<MagicData<T>> getBindLogic() {
        return bindLogic;
    }

    /**
     * 设置该数据的绑定逻辑。
     * <p>
     * 相同的绑定逻辑可以复用同一个实例，这样能节约开支。
     * 在进行Clone时，该成员不会被Clone，而是复用。
     */
    public void setBindLogic(BindDataLogic<MagicData<T>> bindLogic) {
        this.bindLogic = bindLogic;
    }

    /**
     * 会进行深拷贝，所以data成员要求必须实现cloneable接口，并且data类也需要符合深拷贝规范。
     * 否则只能进行浅拷贝。
     *
     * @return 返回新的Clone实例。
     * @throws CloneNotSupportedException
     */
    @Override
    public MagicData<T> clone() throws CloneNotSupportedException {
        MagicData<T> clone = (MagicData<T>) super.clone();
        T data = clone.getData();
        // 在这里检查是否实现了Cloneable接口，如果实现了，则进行深拷贝，如果没有实现，只会进行浅拷贝。
        if (data != null && data instanceof Cloneable) {
            try {
                // 从Object反射并记录住clone()方法，避免每次clone都需要进行反射，提高效率。
                // 所以对MagicData的复用也能提高效率。
                if (cloneMethod == null) {
                    cloneMethod = Object.class.getDeclaredMethod("clone");
                    cloneMethod.setAccessible(true);
                }
                T cloneData = (T) cloneMethod.invoke(data);
                clone.setData(cloneData);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        clone.setBindLogic(bindLogic);
        return clone;
    }

    public static class Builder<T> {

        private boolean isTemplateBuilder;

        private T data;

        private boolean state;
        /**
         * 可以直接使用layout的id作为type
         */
        private int type = -99;

        /**
         * 相同的绑定逻辑可以复用同一个实例，这样能节约开支。
         * 在进行Clone时，该成员不会被Clone，而是复用。
         */
        private BindDataLogic<MagicData<T>> bindLogic;

        private Object tag;

        private StringBuffer templateParams = new StringBuffer();

        public Builder() {

        }

        private Builder(boolean isTemplateBuilder) {
            this.isTemplateBuilder = isTemplateBuilder;
        }


        public Builder setData(T data) {
            this.data = data;
            return this;
        }

        public Builder setState(boolean state) {
            this.state = state;
            return this;
        }

        public Builder setType(int type) {
            if (!templateParams.toString().contains("layoutType")){
                this.type = type;
            }
            return this;
        }

        private Builder setTemplateType(int type){
            if (type != -99){
                templateParams.append("layoutType");
                this.type = type;
            }
            return this;
        }

        public Builder setBindLogic(BindDataLogic<MagicData<T>> bindLogic) {
            if (!templateParams.toString().contains("bindDataLogic")){
                this.bindLogic = bindLogic;
            }
            return this;
        }

        private Builder setTemplateBindLogic(BindDataLogic<MagicData<T>> bindLogic){
            if (bindLogic != null){
                templateParams.append("bindDataLogic");
                this.bindLogic = bindLogic;
            }
            return this;
        }

        public Builder setTag(Object tag) {
            this.tag = tag;
            return this;
        }

        public MagicData build() {
            MagicData magicData = new MagicData(data, state, type, bindLogic);
            magicData.setTag(tag);
            if (isTemplateBuilder) {
                resetParams();
            }
            return magicData;
        }

        private void resetParams() {
            data = null;
            state = false;
            tag = null;
            if (!templateParams.toString().contains("layoutType")) {
                type = -99;
            }
            if (!templateParams.toString().contains("bindDataLogic")) {
                bindLogic = null;
            }
        }
    }

    /**
     * 用于获取MagicData中的data数据，会进行校验。
     *
     * @param magicData
     * @param <T>
     * @return 如果MagicData或者其data数据为null，返回null。需要注意对null处理。
     */
    public static <T> T getData(MagicData<T> magicData){
        T data = null;
        if (magicData != null){
            data = magicData.getData();
        }
        return data;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * 获得一个模版化的Builder，可以定制一个模版，然后用一个Builder实例去创建多个实例。
     * 需要注意的是，模版化的Builder在每次{@link Builder#build()}之后，除了你指定为模版的参数，其余参数都会恢复为默认状态。
     * 并且，如果你使用了一个模版化Builder，那么一旦初始化了其模版参数之后，将不能再修改其模版参数。
     *
     * @param layoutType 布局id。
     * @return 一个支持模版化的Builder实例。
     */
    public static Builder templateBuilder(int layoutType) {
        return templateBuilder(layoutType, null);
    }

    /**
     * 获得一个模版化的Builder，可以定制一个模版，然后用一个Builder实例去创建多个实例。
     * 需要注意的是，模版化的Builder在每次{@link Builder#build()}之后，除了你指定为模版的参数，其余参数都会恢复为默认状态。
     * 并且，如果你使用了一个模版化Builder，那么一旦初始化了其模版参数之后，将不能再修改其模版参数。
     *
     * @param bindDataLogic 数据和UI的绑定逻辑。
     * @return 一个支持模版化的Builder实例。
     */
    public static Builder templateBuilder(BindDataLogic bindDataLogic) {
        return templateBuilder(-99, bindDataLogic);
    }

    /**
     * 获得一个模版化的Builder，可以定制一个模版，然后用一个Builder实例去创建多个实例。
     * 需要注意的是，模版化的Builder在每次{@link Builder#build()}之后，除了你指定为模版的参数，其余参数都会恢复为默认状态。
     * 并且，如果你使用了一个模版化Builder，那么一旦初始化了其模版参数之后，将不能再修改其模版参数。
     *
     * @param layoutType layoutType 布局id，
     * @param bindDataLogic 数据和UI的绑定逻辑。
     * @return 一个支持模版化的Builder实例。
     */
    public static Builder templateBuilder(int layoutType, BindDataLogic bindDataLogic) {
        return new Builder(true).setTemplateType(layoutType).setTemplateBindLogic(bindDataLogic);
    }
}
