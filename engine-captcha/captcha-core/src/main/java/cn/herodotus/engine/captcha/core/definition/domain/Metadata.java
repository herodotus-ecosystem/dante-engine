/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/herodotus/dante-engine>
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.engine.captcha.core.definition.domain;

import java.util.List;

/**
 * <p>Description: 图形验证码元数据 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/11 12:25
 */
public class Metadata {

    /**
     * 滑块拼图验证码生成的带抠图背景图Base64
     */
    private String originalImageBase64;
    /**
     * 滑块拼图验证码滑块拼图Base64
     */
    private String sliderImageBase64;
    /**
     * 滑块拼图验证码抠图位置坐标。
     */
    private Coordinate coordinate;
    /**
     * 文字点选验证码生成的带文字背景图。
     */
    private String wordClickImageBase64;
    /**
     * 文字点选验证码文字坐标信息列表
     */
    private List<Coordinate> coordinates;
    /**
     * 文字点选验证码校验文字
     */
    private List<String> words;
    /**
     * 图形验证码生成的图片
     */
    private String graphicImageBase64;
    /**
     * 图形验证码校验内容
     */
    private String characters;

    public String getOriginalImageBase64() {
        return originalImageBase64;
    }

    public void setOriginalImageBase64(String originalImageBase64) {
        this.originalImageBase64 = originalImageBase64;
    }

    public String getSliderImageBase64() {
        return sliderImageBase64;
    }

    public void setSliderImageBase64(String sliderImageBase64) {
        this.sliderImageBase64 = sliderImageBase64;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getWordClickImageBase64() {
        return wordClickImageBase64;
    }

    public void setWordClickImageBase64(String wordClickImageBase64) {
        this.wordClickImageBase64 = wordClickImageBase64;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public String getGraphicImageBase64() {
        return graphicImageBase64;
    }

    public void setGraphicImageBase64(String graphicImageBase64) {
        this.graphicImageBase64 = graphicImageBase64;
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }
}
