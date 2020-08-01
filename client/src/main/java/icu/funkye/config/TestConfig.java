package icu.funkye.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
/*    @Bean
    public AtGlobalTransactionalInterceptor atGlobalTransactionalInterceptor () {
        return new AtGlobalTransactionalInterceptor();
    }

    @Bean
    public Advisor txAdviceAdvisor(AtGlobalTransactionalInterceptor atGlobalTransactionalInterceptor ) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* icu.funkye.controller.*Controller.*(..))");
        return new DefaultPointcutAdvisor(pointcut, atGlobalTransactionalInterceptor );
    }*/
}
