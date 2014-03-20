precision mediump float;
varying vec3 normalizednormal;
varying vec4 vPosition;
void main() {

	vec3 lightPos = vec3(0.0,2.0,2.0);
	vec3 v = normalize(-vPosition.xyz);
	
	vec3 l = normalize(lightPos - vPosition.xyz);
	vec3 N = normalize(normalizednormal);
	
	vec3 R = reflect(-l,N);
	
	vec3 diffuse = max(dot(N,l),0.0)*vec3(0.5);
	vec3 specular = pow(max(dot(R,v),0.0),128.0)*vec3(0.5);
	vec3 ambient = vec3(0.4);
	vec3 phongshading = diffuse +specular+ ambient;
	
	gl_FragColor = vec4(phongshading,1.0);
}