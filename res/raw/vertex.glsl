attribute vec4 aPosition;
attribute vec3 normal;
uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

varying vec3 normalizednormal;
varying vec4 vPosition;


void main() {

	vPosition = view*model*aPosition;
	normalizednormal =vec3(view*model*vec4(normal,0.0));//vec3(normalize(transpose(inverse(view*model))*vec4(normal,0.0)));
	gl_Position = projection*view*model*aPosition;
}