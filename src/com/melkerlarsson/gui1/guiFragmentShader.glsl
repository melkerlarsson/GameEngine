#version 140

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D guiTexture;

void main(void){

	out_Color = vec4(0.0, 1.0, 0.2, 0.2);
	//ut_Color = texture(guiTexture,textureCoords);

}