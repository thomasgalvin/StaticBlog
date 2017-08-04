<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--social_media_metadata-->
        
        <!--this is dark!-->
        
        <style type="text/css">
            body{
            	margin:40px auto;
            	max-width:650px;
            	line-height:1.6;
            	font-size:18px;
            	background-color:#444;
            	color:#EEEEEE;
            	padding:0 10px
            }
            
            h1,h2,h3{
                line-height:1.2
            }
            
            h1 small{
            	font-size: 20px;
            }
            
            img,object{
                 max-width:100%;
                 height:auto;
            }
            
            a{
            	color:#07a;
            	text-decoration: none;
            }
            
            a:visited{
            	color:#8e8e8e;
            }
            
            a:hover{
            	text-decoration: underline;
            }
        </style>
        
        <title>
            <#if post??>
                <#if post.title??>
                    ${post.title }
                </#if> 
                <#if site.title??> - </#if>
            <#elseif posts??>
                <#if category??>
                    Posts tagged '${category}'
                <#else>
                    All posts
                </#if>
                <#if site.title??> - </#if>
            </#if>
            <#if site.title??>${site.title}</#if>
            <#if site.subtitle??> - ${site.subtitle}</#if>
        </title>
    </head>
    <body>
        <!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
        
        <!-- header and navbar -->
        <header>
            <div class="site-title">
               <#if site.title??>
                   <h1 class="masthead">
                       <a href="${site.blogRoot}/">${site.title}
                           <#if site.subtitle??>
                               <br>
                               <small>${site.subtitle}</small>
                           </#if>
                       </a>
                   </h1>
               </#if>
            </div>
        </header>
        <!-- end header and navbar -->
        
        <!-- main body -->
        <div class="main-body">
            <#if post??>
                <article>
                    <#if post.bannerImage??>
                        <img src="${post.bannerImage}" <#if post.title??>alt="${post.title}" title="${post.title}"</#if> class="banner_image" />
                    </#if>
                    
                    <#if post.html??>
                        ${post.html}
                    </#if>
                </article>
            </#if>
        </div>
        <!-- end main body -->
        
        <!-- footer -->
        <footer>
            Copyright &copy;
            All Rights Reserved &bull;
            <navigation>
                <a href="${site.blogRoot}/">Home</a> &bull;
                <a href="/${site.history}/1">All posts</a>
            </navigation>
        </footer>
        <!-- end footer -->
    </body>
</html>
