# HTML常用标签

## input

1.  class 属性

    class 属性用来引用自定义页面的类样式。即你先在\<style type="text/css">\</style>标签内定义一个类样式，然后再来引用它。

    ```html
    <style type="text/css">
    .btn{
            color:red;
    }
    </style>
    <input type="button" class="btn" />
    ```

    ​

## td

1.  colspan 属性

    colspan 属性规定单元格可横跨的列数。`<td colspan='value'>`

## table

## select

1.  multiple 属性

    multiple 属性规定可同时选择多个选项。`<select multiple="value">`

    可以把 multiple 属性与 size 属性配合使用，来定义可见选项的数目。

## HTML事件属性

### From 事件

由 HTML 表单内的动作触发的事件（应用到几乎所有 HTML 元素，但最常用在 form 元素中）：

1.  onchange 属性

    onchange 在元素值改变时触发。`<element onchange="script">`

    onchange 属性适用于：\<input>、\<textarea> 以及 \<select> 元素。