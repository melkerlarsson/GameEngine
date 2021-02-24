#version 140

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D guiTexture;
uniform vec3 color;
uniform float opacity;

void main(void){

    out_Color = vec4(color, opacity);
    //ut_Color = texture(guiTexture,textureCoords);

}